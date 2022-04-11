package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET, value = "/history")
    ResponseEntity<TicketListDto> getTicketHistory(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.POST, value = "/theme")
    ResponseEntity<Object> changeTheme(@RequestHeader("jwt") String jwt, @RequestHeader("theme") Boolean darkTheme);

    @RequestMapping(method = RequestMethod.POST, value = "/user-reg")
    ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("jwt") String jwt);
}
