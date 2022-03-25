package com.domanov.ticketservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "ticket", schema = "public")
public class Ticket {

    @Id
    @GeneratedValue
    private Integer id;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "ticket_uid", nullable = false, unique = true)
    private UUID ticket_uid;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "show_uid", nullable = false, unique = true)
    private UUID show_uid;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(UUID ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public UUID getShow_uid() {
        return show_uid;
    }

    public void setShow_uid(UUID show_uid) {
        this.show_uid = show_uid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
