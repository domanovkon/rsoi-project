package com.domanov.gatewayservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "museum", url = "http://localhost:8081/api/v1")
public interface MuseumClient {

    @RequestMapping(method = RequestMethod.GET, value = "/museums")
    Object getMuseums();
}
