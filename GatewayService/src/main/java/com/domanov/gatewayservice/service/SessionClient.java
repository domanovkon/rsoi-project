package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "session", url = "http://localhost:8084/api/v1")
public interface SessionClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    UserResponse getUser(@RequestParam("login") String login);

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    ValidateToken validate(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.POST, value = "/theme")
    ResponseEntity<Object> changeTheme(@RequestHeader("login") String login, @RequestHeader("theme") Boolean darkTheme);

    @RequestMapping(method = RequestMethod.PUT, value = "/user-stat")
    List<UserStatDto> getUserStat(@RequestBody List<UserStatDto> userStatDtoList);

    @RequestMapping(method = RequestMethod.GET, value = "/theme")
    AuthResponse getTheme(@RequestHeader("jwt") String login);
}
