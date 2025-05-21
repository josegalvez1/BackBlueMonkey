package blueMonkey.security.controller;



import blueMonkey.security.dto.AuthLoginRequest;
import blueMonkey.security.dto.AuthReponse;
import blueMonkey.user.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userServiceImpl;

    @PostMapping("/log-in")
    public ResponseEntity<AuthReponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userServiceImpl.loginUser(userRequest), HttpStatus.OK);
    }
}
