package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.MuseumPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gateway", url = "http://localhost:8080/api/v1")
public interface GatewayClient {

    @RequestMapping(method = RequestMethod.GET, value = "/museums")
    ResponseEntity<MuseumPageResponse> getMuseums(@RequestHeader("jwt") String jwt, @RequestParam("page") int page, @RequestParam("size") int size);
}
