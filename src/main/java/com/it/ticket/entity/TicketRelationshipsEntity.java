package com.it.ticket.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ticket_relationships")
public class TicketRelationshipsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "source_ticket_id", nullable = false)
    private UUID sourceTicketId;
    
    @Column(name = "target_ticket_id", nullable = false)
    private UUID targetTicketId;

    @Column(name = "relationship_type", nullable = false, length = 50)
    private String relationshipType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_by_id")
    private UUID createdById;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
