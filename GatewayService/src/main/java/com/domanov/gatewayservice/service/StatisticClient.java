package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.AddStatRequest;
import com.domanov.gatewayservice.dto.MuseumResponse;
import com.domanov.gatewayservice.dto.TicketBuyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "statistic", url = "http://localhost:8082/api/v1")
public interface StatisticClient {

    @RequestMapping(method = RequestMethod.POST, value = "/transfer")
    ResponseEntity<Object> moneyTransfer(@RequestBody AddStatRequest addStatRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/user-reg")
    ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("uid") String uid);
}
