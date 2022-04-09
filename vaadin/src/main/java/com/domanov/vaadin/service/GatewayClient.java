package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.MuseumInfoResponse;
import com.domanov.vaadin.dto.MuseumPageResponse;
import com.domanov.vaadin.dto.TicketBuyRequest;
import com.domanov.vaadin.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.JobKOctets;

@FeignClient(name = "gateway", url = "http://localhost:8080/api/v1")
public interface GatewayClient {

    @RequestMapping(method = RequestMethod.GET, value = "/museums")
    ResponseEntity<MuseumPageResponse> getMuseums(@RequestHeader("jwt") String jwt, @RequestParam("page") int page, @RequestParam("size") int size);

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    ResponseEntity<UserResponse> getUser(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.GET, value = "/museum")
    ResponseEntity<MuseumInfoResponse> getMuseumInfo(@RequestHeader("jwt") String jwt, @RequestParam("uid") String museum_uid);

    @RequestMapping(method = RequestMethod.POST, value = "/tickets")
    ResponseEntity<Object> buyTicket(@RequestHeader("jwt") String jwt, @RequestBody TicketBuyRequest ticketBuyRequest);
}
