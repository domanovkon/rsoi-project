package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.MuseumPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "museum", url = "http://localhost:8081/api/v1")
public interface MuseumClient {

    @RequestMapping(method = RequestMethod.GET, value = "/museums")
    ResponseEntity<MuseumPageResponse> getMuseums(@RequestParam("page") int page, @RequestParam("size") int size);

}
