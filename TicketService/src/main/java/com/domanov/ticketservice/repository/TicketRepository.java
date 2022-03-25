package com.domanov.ticketservice.repository;

import com.domanov.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
