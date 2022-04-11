package com.domanov.gatewayservice.dto;

import java.time.LocalDateTime;

public class TicketHistory {

    private String museumName;

    private String showName;

    private String show_uid;

    private Integer price;

    private LocalDateTime date;

    public String getMuseumName() {
        return museumName;
    }

    public void setMuseumName(String museumName) {
        this.museumName = museumName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getShow_uid() {
        return show_uid;
    }

    public void setShow_uid(String show_uid) {
        this.show_uid = show_uid;
    }
}
