package blueMonkey.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    /* CREAR TOKEN, se codifica el token*/
    public String createToken(Authentication authentication){
        //le paso la clave token, que he creado
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        //le paso el username
        String username = authentication.getPrincipal().toString();

        //Obtiene todas las autorizaciones, me las devuelve como String y con el collector va a tomar cada uno de los permisos y los separa con la ,
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 3600000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))   //sea valido a partir de tal hora
                .sign(algorithm);
    }

    /* Compueba si el jwt es correcto y Devuelve el JWT descodificado */
    public DecodedJWT validateToken(String token){
        try{
            System.out.println(">>> Validando token con clave: " + privateKey); // DEBUG

            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator).build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        }catch (JWTVerificationException exception){
            throw new JWTVerificationException("El token es invalido, no autorizado");
        }
    }

    /* Extrae el username del token */
    public String extractUsername(DecodedJWT decodedJWT){
        //Obtengo el subject del token, que es el username
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    /* Obtener todos los claim */
    public Map<String, Claim>returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }

}
