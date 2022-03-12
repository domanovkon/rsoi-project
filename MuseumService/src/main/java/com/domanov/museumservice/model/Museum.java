package com.domanov.museumservice.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "museum")
public class Museum {

    @Id
    @GeneratedValue
    @Column(name = "museum_uid")
    private UUID ticket_uid;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "city", length = 255, nullable = false)
    private String city;

    @Column(name = "country", length = 255, nullable = false)
    private String country;

    @Column(name = "description", length = 100)
    private String description;


    public UUID getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(UUID ticket_uid) {
        this.ticket_uid = ticket_uid;
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
}
