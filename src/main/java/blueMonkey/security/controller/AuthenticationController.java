package blueMonkey.security.controller;



import blueMonkey.security.dto.AuthLoginRequest;
import blueMonkey.security.dto.AuthReponse;
import blueMonkey.user.application.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/log-in")
    public ResponseEntity<AuthReponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userServiceImpl.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequest loginRequest, HttpServletResponse response) {
        AuthReponse authResponse = userServiceImpl.loginUser(loginRequest);

        if (!authResponse.status()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        }
        ResponseCookie cookie = ResponseCookie.from("jwt", authResponse.jwt())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        // ✅ No devolvemos el JWT en el body
        return ResponseEntity.ok().body(Map.of(
                "username", authResponse.username(),
                "roles", authResponse.roles(),
                "jwt", authResponse.jwt()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthReponse> register(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userServiceImpl.register(userRequest), HttpStatus.OK);
    }
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return ResponseEntity.ok(Map.of(
                "username", username,
                "roles", roles
        ));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", deleteCookie.toString())
                .body("Sesión cerrada");
    }
}
