package com.domanov.vaadin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MuseumPageResponse {
    private int page;

    private int pageSize;

    private int totalElements;

    private List<MuseumResponse> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    @JsonProperty(value = "items")
    public List<MuseumResponse> getItems() {
        return items;
    }

    public void setItems(List<MuseumResponse> items) {
        this.items = items;
    }
}
