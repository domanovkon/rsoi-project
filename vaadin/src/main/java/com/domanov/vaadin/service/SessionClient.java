package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.RegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "session", url = "http://localhost:8084/api/v1")
public interface SessionClient {

    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    Object registration(@RequestBody RegistrationRequest registrationRequest);
}
