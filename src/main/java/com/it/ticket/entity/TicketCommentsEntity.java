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
@Table(name = "ticket_comments")
public class TicketCommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ticket_id", nullable = false)
    private UUID ticketId;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "is_internal", nullable = false)
    private Boolean isInternal;

    @Column(name = "is_system_generated", nullable = false)
    private Boolean isSystemGenerated;

    @Column(name = "comment_type", length = 50, nullable = false)
    private String commentType;

    @Column(name = "time_spent_minutes", nullable = false)
    private Integer timeSpentMinutes;

    @Column(name = "attachments", columnDefinition = "JSONB")
    private Object attachments;

    @Column(name = "mentions", columnDefinition = "JSONB")
    private Object mentions;

    @Column(name = "edited_at")
    private Instant editedAt;

    @Column(name = "edited_by_id")
    private UUID editedById;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
