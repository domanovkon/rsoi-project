package com.domanov.ticketservice.controller;

import com.domanov.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("https://localhost:8083/api/v1")
@RequestMapping("api/v1")
public class TicketController {

    @Autowired
    private TicketService ticketService;
}
