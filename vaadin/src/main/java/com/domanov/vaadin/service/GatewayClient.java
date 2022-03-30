package com.domanov.vaadin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "gateway", url = "http://localhost:8080/api/v1")
public interface GatewayClient {

    @RequestMapping(method = RequestMethod.GET, value = "/museums")
    Object getMuseums();

}
