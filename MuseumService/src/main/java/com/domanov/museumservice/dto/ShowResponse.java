package com.domanov.museumservice.dto;

import java.time.LocalDateTime;

public class ShowResponse {

    private String name;

    private String show_uid;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean permanentExhibition;

    private Integer price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShow_uid() {
        return show_uid;
    }

    public void setShow_uid(String show_uid) {
        this.show_uid = show_uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getPermanentExhibition() {
        return permanentExhibition;
    }

    public void setPermanentExhibition(Boolean permanentExhibition) {
        this.permanentExhibition = permanentExhibition;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
