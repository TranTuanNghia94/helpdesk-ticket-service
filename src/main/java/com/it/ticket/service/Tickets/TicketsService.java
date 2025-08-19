package com.it.ticket.service.Tickets;

import org.springframework.stereotype.Service;

import com.it.ticket.entity.TicketsEntity;
import com.it.ticket.mapper.TicketMapper;
import com.it.ticket.model.Ticket.CreateTicket;
import com.it.ticket.repository.TicketRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketsService {
    private final TicketRepository ticketRepo;
    private final TicketMapper ticketMapper;

    public String createTicket(CreateTicket createTicket) {
        try {
            log.info("Start creating ticket: {}", createTicket);

            // map to entity
            TicketsEntity ticket = ticketMapper.toEntity(createTicket);
            ticketRepo.save(ticket);

            log.info("Ticket created successfully: {}", ticket.getTicketNumber());
            
            return ticket.getTicketNumber();
        } catch (Exception e) {
            log.error("Error creating ticket: {}", e.getMessage());
            throw new RuntimeException("Error creating ticket", e);
        }
    }
}
