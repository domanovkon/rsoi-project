package com.domanov.ticketservice.service;

import com.domanov.ticketservice.dto.TicketBuyRequest;
import com.domanov.ticketservice.dto.TicketDto;
import com.domanov.ticketservice.dto.TicketListDto;
import com.domanov.ticketservice.dto.TicketResponse;
import com.domanov.ticketservice.model.Ticket;
import com.domanov.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service("TicketService")
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public ResponseEntity<TicketResponse> buyTicket(String user_uid, TicketBuyRequest ticketBuyRequest) {
        try {
            List<Ticket> ticketList = new ArrayList<>();
            for (int i = 0; i < ticketBuyRequest.getAmount(); i++) {
                Ticket ticket = new Ticket();
                ticket.setPrice(ticketBuyRequest.getPrice());
                ticket.setShow_uid(UUID.fromString(ticketBuyRequest.getShow_uid()));
                ticket.setUser_uid(UUID.fromString(user_uid));
                ticket.setTicket_uid(UUID.randomUUID());
                ticket.setBuyDate(LocalDateTime.now());
                ticketList.add(ticket);
            }
            TicketResponse ticketResponse = new TicketResponse();
            List<String> ticket_uids = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                ticketRepository.save(ticket);
                ticket_uids.add(ticket.getTicket_uid().toString());
            }
            ticketResponse.setTickets_uid(ticket_uids);
            return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new TicketResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    public ResponseEntity<TicketListDto> getTicketHistory(String user_uid) {
        List<Ticket> ticketList = ticketRepository.findAllUserTickets(UUID.fromString(user_uid));
        if (ticketList.size() != 0) {
            List<TicketDto> ticketDtos = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                TicketDto ticketDto = new TicketDto();
                ticketDto.setShow_uid(ticket.getShow_uid().toString());
                ticketDto.setPrice(ticket.getPrice());
                String date = DateTimeFormatter.ofPattern("d MMMM yyyy")
                        .withLocale(new Locale("ru"))
                        .format((ticket.getBuyDate().toLocalDate()));
                ticketDto.setDate(date);
                ticketDto.setDateTime(ticket.getBuyDate());
                ticketDtos.add(ticketDto);
            }
            TicketListDto ticketListDto = new TicketListDto();
            ticketListDto.setTicketList(ticketDtos);
            return new ResponseEntity<>(ticketListDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new TicketListDto(), HttpStatus.OK);
        }
    }
}
