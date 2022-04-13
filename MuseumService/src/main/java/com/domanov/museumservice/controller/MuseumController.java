package com.domanov.museumservice.controller;

import com.domanov.museumservice.dto.*;
import com.domanov.museumservice.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:8081/api/v1")
@RequestMapping("api/v1")
public class MuseumController {

    @Autowired
    private MuseumService museumService;

    @GetMapping("/museums")
    @CrossOrigin(origins = "*")
    public MuseumPageResponse getMuseums(@RequestParam("page") int page, @RequestParam("size") int size) {
        return museumService.getMuseums(page, size);
    }

    @GetMapping("/museum")
    @CrossOrigin(origins = "*")
    public MuseumInfoResponse getMuseumInfo(@RequestParam("uid") String uid) {
        return museumService.getMuseumInfo(uid);
    }

    @PostMapping("/tickets")
    public ResponseEntity<MuseumResponse> buyTicket(@RequestBody TicketBuyRequest ticketBuyRequest) {
        return museumService.buyTicket(ticketBuyRequest);
    }

    @PutMapping("/history")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TicketListDto> getShowMuseumList(@RequestBody TicketListDto ticketListDto) {
        return museumService.getShowMuseumList(ticketListDto);
    }

    @PutMapping("/transfer")
    @CrossOrigin(origins = "*")
    public List<MoneyTransferDto> getMoneyTransfer(@RequestBody List<MoneyTransferDto> moneyTransferDtoList) {
        return museumService.getMoneyTransfer(moneyTransferDtoList);
    }

    @GetMapping("/types")
    @CrossOrigin(origins = "*")
    public List<MyseumTypeDto> getMuseumTypes() {
        return museumService.getMuseumTypes();
    }

    @PostMapping("/new-museum")
    public ResponseEntity<MuseumInfoResponse> createMuseum(@RequestBody MuseumInfoResponse museumInfoResponse) {
        return museumService.createMuseum(museumInfoResponse);
    }

    @DeleteMapping("/show")
    public ResponseEntity<Object> removeShow(@RequestHeader("uid") String uid) {
        return museumService.removeShow(uid);
    }

    @DeleteMapping("/museum")
    public ResponseEntity<Object> removeMuseum(@RequestHeader("uid") String uid) {
        return museumService.removeMuseum(uid);
    }

    @PostMapping("/show")
    public ResponseEntity<ShowResponse> createShow(@RequestBody ShowResponse showResponse) {
        return museumService.createShow(showResponse);
    }
}
