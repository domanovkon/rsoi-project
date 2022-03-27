package com.domanov.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayServiceApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", System.getenv("PORT")));
        app.run(args);
    }

}
