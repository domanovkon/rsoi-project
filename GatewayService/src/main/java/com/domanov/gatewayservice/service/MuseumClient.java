package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "museum", url = "http://localhost:8081/api/v1")
public interface MuseumClient {

    @RequestMapping(method = RequestMethod.GET, value = "/museums")
    MuseumPageResponse getMuseums(@RequestParam("page") int page, @RequestParam("size") int size);

    @RequestMapping(method = RequestMethod.GET, value = "/museum")
    MuseumInfoResponse getMuseumInfo(@RequestParam("uid") String uid);

    @RequestMapping(method = RequestMethod.POST, value = "/tickets")
    ResponseEntity<MuseumResponse> buyTicket(@RequestBody TicketBuyRequest ticketBuyRequest);

    @RequestMapping(method = RequestMethod.PUT, value = "/history")
    ResponseEntity<TicketListDto> getShowMuseumList(@RequestBody TicketListDto ticketListDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/transfer")
    List<MoneyTransferDto> getMoneyTransfer(@RequestBody List<MoneyTransferDto> moneyTransferDtoList);

    @RequestMapping(method = RequestMethod.GET, value = "/types")
    List<MyseumTypeDto> getMuseumTypes();

    @RequestMapping(method = RequestMethod.POST, value = "/new-museum")
    ResponseEntity<MuseumInfoResponse> createMuseum(@RequestBody MuseumInfoResponse museumInfoResponse);

    @RequestMapping(method = RequestMethod.DELETE, value = "/show")
    ResponseEntity<Object> removeShow(@RequestHeader("uid") String uid);

    @RequestMapping(method = RequestMethod.DELETE, value = "/museum")
    ResponseEntity<Object> removeMuseum(@RequestHeader("uid") String uid);

    @RequestMapping(method = RequestMethod.POST, value = "/show")
    ResponseEntity<ShowResponse> createShow(@RequestBody ShowResponse showResponse);

    @RequestMapping(method = RequestMethod.POST, value = "/museum")
    ResponseEntity<MuseumInfoResponse> changeMuseum(@RequestBody MuseumInfoResponse museumInfoResponse);
}
