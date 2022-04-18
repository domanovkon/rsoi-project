package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET, value = "/transfer")
    ResponseEntity<List<MoneyTransferDto>> getMoneyTransfer(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.GET, value = "/user-stat")
    ResponseEntity<List<UserStatDto>> getUserStat(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.GET, value = "/types")
    ResponseEntity<List<MyseumTypeDto>> getMuseumTypes(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.POST, value = "/new-museum")
    ResponseEntity<MuseumInfoResponse> createMuseum(@RequestHeader("jwt") String jwt, @RequestBody MuseumInfoResponse museumInfoResponse);

    @RequestMapping(method = RequestMethod.DELETE, value = "/show")
    ResponseEntity<Object> removeShow(@RequestHeader("jwt") String jwt, @RequestHeader("uid") String uid);

    @RequestMapping(method = RequestMethod.DELETE, value = "/museum")
    ResponseEntity<Object> removeMuseum(@RequestHeader("jwt") String jwt, @RequestHeader("uid") String uid);

    @RequestMapping(method = RequestMethod.POST, value = "/show")
    ResponseEntity<ShowResponse> createShow(@RequestHeader("jwt") String jwt, @RequestBody ShowResponse showResponse);

    @RequestMapping(method = RequestMethod.POST, value = "/museum")
    ResponseEntity<MuseumInfoResponse> changeMuseum(@RequestHeader("jwt") String jwt, @RequestBody MuseumInfoResponse museumInfoResponse);

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    ValidateToken validate(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.GET, value = "/theme")
    ResponseEntity<AuthResponse> getTheme(@RequestHeader("jwt") String jwt);
}
