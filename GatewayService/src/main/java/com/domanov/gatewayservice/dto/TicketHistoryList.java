package com.domanov.gatewayservice.dto;

import java.util.List;

public class TicketHistoryList {

    private List<TicketHistory> ticketHistories;

    public List<TicketHistory> getTicketHistories() {
        return ticketHistories;
    }

    public void setTicketHistories(List<TicketHistory> ticketHistories) {
        this.ticketHistories = ticketHistories;
    }
}
