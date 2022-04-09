package com.domanov.statisticservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "money_transfer", schema = "public")
public class MoneyTransfer {

    @Id
    @GeneratedValue
    private Integer id;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "ticket_uid", nullable = false, unique = true)
    private UUID ticket_uid;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "museum_uid", nullable = false)
    private UUID museum_uid;

    @Column(name = "accrual", nullable = false)
    private Integer accrual;

    @Column(name = "date_of_transfer")
    private LocalDateTime dateOfTransfer;

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

    public Integer getAccrual() {
        return accrual;
    }

    public void setAccrual(Integer accrual) {
        this.accrual = accrual;
    }

    public UUID getMuseum_uid() {
        return museum_uid;
    }

    public void setMuseum_uid(UUID museum_uid) {
        this.museum_uid = museum_uid;
    }

    public LocalDateTime getDateOfTransfer() {
        return dateOfTransfer;
    }

    public void setDateOfTransfer(LocalDateTime dateOfTransfer) {
        this.dateOfTransfer = dateOfTransfer;
    }
}
