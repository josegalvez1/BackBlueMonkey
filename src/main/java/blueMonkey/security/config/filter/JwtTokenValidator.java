package blueMonkey.security.config.filter;


import blueMonkey.security.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/* extender de esta clase hace que siempre que mande una peticion pase por este filtro, garantiza que siempre necesita la validacion del token  */
public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        // Obtener token del header Authorization
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si no está en header, buscar en cookie "jwt" usando el método privado
        if (jwtToken == null || jwtToken.isEmpty()) {
            jwtToken = getTokenFromCookies(request);
        }

        // Si jwtToken viene del header, puede empezar con "Bearer ", si viene de cookie no
        if (jwtToken != null && !jwtToken.isEmpty()) {
            if (jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7);
            }

            try {
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
                String username = jwtUtils.extractUsername(decodedJWT);

                List<String> roles = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asList(String.class);
                Collection<? extends GrantedAuthority> authorities =
                        roles.stream()
                                .map(role -> (GrantedAuthority) () -> role)
                                .toList();

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Token inválido o no autorizado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}