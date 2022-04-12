package com.domanov.museumservice.dto;

import java.time.LocalDateTime;

public class MoneyTransferDto {

    private String ticket_uid;

    private String museum_uid;

    private String museumName;

    private Integer accrual;

    private LocalDateTime dateTime;

    private String date;

    public String getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(String ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public String getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(String museum_uid) {
        this.museum_uid = museum_uid;
    }

    public Integer getAccrual() {
        return accrual;
    }

    public void setAccrual(Integer accrual) {
        this.accrual = accrual;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
