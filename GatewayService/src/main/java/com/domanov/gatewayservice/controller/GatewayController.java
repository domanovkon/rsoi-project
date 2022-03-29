package com.domanov.gatewayservice.controller;

import com.domanov.gatewayservice.dto.UserResponse;
import com.domanov.gatewayservice.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("https://localhost:8080/api/v1")
@RequestMapping("api/v1")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public UserResponse getUser(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException, InterruptedException {
        return gatewayService.getUser(username, password);
    }

    @GetMapping("/museums")
    @CrossOrigin(origins = "*")
    public Object getAllMuseums(@RequestHeader("jwt") String jwt) throws IOException, InterruptedException {
        return gatewayService.getAllMuseums(jwt);
    }

}
