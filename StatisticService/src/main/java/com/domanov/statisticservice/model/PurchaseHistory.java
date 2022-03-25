package com.domanov.statisticservice.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase_history", schema = "public")
public class PurchaseHistory {

    @Id
    @GeneratedValue
    private Integer id;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "user_uid", nullable = false, unique = true)
    private UUID user_uid;

    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "ticket_uid", nullable = false, unique = true)
    private UUID ticket_uid;

    @Column(name = "dateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "accrual", nullable = false)
    private Integer accrual;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(UUID user_uid) {
        this.user_uid = user_uid;
    }

    public UUID getTicket_uid() {
        return ticket_uid;
    }

    public void setTicket_uid(UUID ticket_uid) {
        this.ticket_uid = ticket_uid;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAccrual() {
        return accrual;
    }

    public void setAccrual(Integer accrual) {
        this.accrual = accrual;
    }
}
