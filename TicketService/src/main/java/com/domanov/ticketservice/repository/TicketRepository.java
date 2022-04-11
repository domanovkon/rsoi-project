package com.domanov.ticketservice.repository;

import com.domanov.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT f FROM Ticket f WHERE f.user_uid = ?1")
    List<Ticket> findAllUserTickets(UUID user_uid);
}
