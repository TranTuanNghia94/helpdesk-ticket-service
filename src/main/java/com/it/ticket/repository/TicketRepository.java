package com.it.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import com.it.ticket.entity.TicketsEntity;

@Repository
public interface TicketRepository extends JpaRepository<TicketsEntity, UUID> {

}
