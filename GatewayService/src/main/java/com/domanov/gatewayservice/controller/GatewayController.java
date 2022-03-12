package com.domanov.gatewayservice.controller;

import com.domanov.gatewayservice.dto.Hello;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:8080/api/v1")
@RequestMapping("api/v1")
public class GatewayController {

    @GetMapping("/hello")
    @CrossOrigin(origins = "*")
    public Hello getHello() {
        Hello hello =  new Hello();
        hello.setHello("hi!!");
        return hello;
    }
}
