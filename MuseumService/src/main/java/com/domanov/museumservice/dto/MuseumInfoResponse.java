package com.domanov.museumservice.dto;

import java.util.List;

public class MuseumInfoResponse {

    private String museum_uid;

    private String name;

    private String description;

    private AddressResponse address;

    private String type;

    private String legalEntityName;

    private String inn;

    private String ogrn;

    private String email;

    private List<ShowResponse> shows;

    public String getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(String museum_uid) {
        this.museum_uid = museum_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLegalEntityName() {
        return legalEntityName;
    }

    public void setLegalEntityName(String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ShowResponse> getShows() {
        return shows;
    }

    public void setShows(List<ShowResponse> shows) {
        this.shows = shows;
    }
}
