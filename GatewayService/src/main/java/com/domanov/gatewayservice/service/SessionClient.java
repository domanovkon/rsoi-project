package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.UserResponse;
import com.domanov.gatewayservice.dto.ValidateToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "session", url = "http://localhost:8084/api/v1")
public interface SessionClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    UserResponse getUser(@RequestParam("login") String login);

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    ValidateToken validate(@RequestHeader("jwt") String jwt);
}
