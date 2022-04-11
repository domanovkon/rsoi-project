package com.domanov.ticketservice.controller;

import com.domanov.ticketservice.dto.TicketBuyRequest;
import com.domanov.ticketservice.dto.TicketDto;
import com.domanov.ticketservice.dto.TicketListDto;
import com.domanov.ticketservice.dto.TicketResponse;
import com.domanov.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("https://localhost:8083/api/v1")
@RequestMapping("api/v1")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/tickets")
    public ResponseEntity<TicketResponse> buyTicket(@RequestHeader("user_uid") String user_uid, @RequestBody TicketBuyRequest ticketBuyRequest) {
        return ticketService.buyTicket(user_uid, ticketBuyRequest);
    }

    @GetMapping("/history")
    @CrossOrigin(origins = "*")
    public ResponseEntity<TicketListDto> getTicketHistory(@RequestHeader("user_uid") String user_uid) {
        return ticketService.getTicketHistory(user_uid);
    }
}
