package com.domanov.museumservice.dto;

import java.util.List;

public class TicketListDto {

    private List<TicketDto> ticketList;

    public List<TicketDto> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketDto> ticketList) {
        this.ticketList = ticketList;
    }
}
