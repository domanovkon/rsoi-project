package com.domanov.sessionservice.controller;

import com.domanov.sessionservice.dto.AuthResponse;
import com.domanov.sessionservice.dto.RegistrationRequest;
import com.domanov.sessionservice.dto.UserResponse;
import com.domanov.sessionservice.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:8084/api/v1")
@RequestMapping("api/v1")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public AuthResponse getAuth(@RequestParam("username") String username, @RequestParam("password") String password) {
        return sessionService.getAuth(username, password);
    }

    @PostMapping("/registration")
    @CrossOrigin(origins = "*")
    public AuthResponse registration(@RequestBody RegistrationRequest registrationRequest) {
        return sessionService.registration(registrationRequest);
    }
}
