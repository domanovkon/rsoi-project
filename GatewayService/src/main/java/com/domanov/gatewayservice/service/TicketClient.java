package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.TicketBuyRequest;
import com.domanov.gatewayservice.dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ticket", url = "http://localhost:8083/api/v1")
public interface TicketClient {

    @RequestMapping(method = RequestMethod.POST, value = "/tickets")
    ResponseEntity<TicketResponse> buyTicket(@RequestHeader("user_uid") String user_uid, @RequestBody TicketBuyRequest ticketBuyRequest);
}
