package com.domanov.gatewayservice.proxy;

import com.domanov.gatewayservice.dto.UserResponse;
import com.domanov.gatewayservice.service.GatewayService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Component
//@FeignClient(name = "SessionServiceProxy", url = "http://localhost:8084/api/v1")
//public interface SessionServiceProxy {
//
//    @GetMapping("/authenticate")
//    ResponseEntity<UserResponse> getUser(@RequestParam(name = "username", required = false) String username, @RequestParam(name = "password", required = false) String password);
//}
