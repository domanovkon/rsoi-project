package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.AuthResponse;
import com.domanov.vaadin.dto.RegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "session", url = "http://localhost:8084/api/v1")
public interface SessionClient {

    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    ResponseEntity<AuthResponse> registration(@RequestBody RegistrationRequest registrationRequest);

    @RequestMapping(method = RequestMethod.GET, value = "/authenticate")
    ResponseEntity<AuthResponse> authorization(@RequestParam String username, @RequestParam String password);

    @RequestMapping(method = RequestMethod.GET, value = "/user-check")
    ResponseEntity userCheck(@RequestParam String username);
}
