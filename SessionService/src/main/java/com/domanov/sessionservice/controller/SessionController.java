package com.domanov.sessionservice.controller;

import com.domanov.sessionservice.dto.AuthResponse;
import com.domanov.sessionservice.dto.RegistrationRequest;
import com.domanov.sessionservice.dto.UserResponse;
import com.domanov.sessionservice.dto.ValidateToken;
import com.domanov.sessionservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:8084/api/v1")
@RequestMapping("api/v1")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponse> getAuth(@RequestParam("username") String username, @RequestParam("password") String password) {
        return sessionService.getAuth(username, password);
    }

    @PostMapping("/registration")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponse> registration(@RequestBody RegistrationRequest registrationRequest) {
        return sessionService.registration(registrationRequest);
    }

    @GetMapping("/user-check")
    @CrossOrigin(origins = "*")
    public ResponseEntity userCheck(@RequestParam("username") String username) {
        return sessionService.userCheck(username);
    }

    @GetMapping("/validate")
    @CrossOrigin(origins = "*")
    public ValidateToken validate(@RequestHeader("jwt") String jwt) {
        return sessionService.validate(jwt);
    }

    @GetMapping("/user")
    @CrossOrigin(origins = "*")
    public UserResponse getUser(@RequestParam("login") String login) {
        UserResponse userResponse = sessionService.getUser(login);
        if (userResponse != null) {
            return userResponse;
        }
        return new UserResponse();
    }

    @PostMapping("/theme")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> changeTheme(@RequestHeader("login") String login, @RequestHeader("theme") Boolean darkTheme) {
        return sessionService.changeTheme(login, darkTheme);
    }
}
