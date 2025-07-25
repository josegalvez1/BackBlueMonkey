package blueMonkey.security.controller;



import blueMonkey.security.dto.AuthLoginRequest;
import blueMonkey.security.dto.AuthReponse;
import blueMonkey.user.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public ResponseEntity<AuthReponse> register(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userServiceImpl.register(userRequest), HttpStatus.OK);
    }
}
