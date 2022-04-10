package com.domanov.ticketservice.dto;

import java.util.List;

public class TicketResponse {

    private List<String> tickets_uid;

    public List<String> getTickets_uid() {
        return tickets_uid;
    }

    public void setTickets_uid(List<String> tickets_uid) {
        this.tickets_uid = tickets_uid;
    }
}
