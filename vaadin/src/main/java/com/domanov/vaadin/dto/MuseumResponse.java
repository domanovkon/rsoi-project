package com.domanov.vaadin.dto;

import java.util.UUID;

public class MuseumResponse {

    private UUID museum_uid;

    private String name;

    private String city;

    private String country;

    private String description;

    private String type;

    private AddressResponse address;

    public UUID getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(UUID museum_uid) {
        this.museum_uid = museum_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }
}
