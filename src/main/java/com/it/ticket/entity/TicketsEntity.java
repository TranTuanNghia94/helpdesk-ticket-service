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
@Table(name = "tickets")
public class TicketsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;    

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "ticket_number", unique = true, length = 50, nullable = false)
    private String ticketNumber;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "requester_id", nullable = false)
    private UUID requesterId;

    @Column(name = "requester_email", length = 255)
    private String requesterEmail;

    @Column(name = "requester_phone", length = 50)
    private String requesterPhone;


    @Column(name = "assigned_to_id")
    private UUID assignedToId;

    @Column(name = "assigned_team_id")
    private UUID assignedTeamId;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "assigned_by_id")
    private UUID assignedById;


    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "subcategory_id")
    private UUID subcategoryId;

    @Column(name = "priority_id", nullable = false)
    private UUID priorityId;

    @Column(name = "status_id", nullable = false)
    private UUID statusId;

    @Column(name = "ticket_type", length = 50, nullable = false)
    private String ticketType;

    @Column(name = "source", length = 50, nullable = false)
    private String source;

    @Column(name = "affected_asset_id")
    private UUID affectedAssetId;

    @Column(name = "configuration_items", columnDefinition = "JSONB")
    private Object configurationItems;

    @Column(name = "urgency", length = 50, nullable = false)
    private String urgency;

    @Column(name = "impact", length = 50, nullable = false)
    private String impact;

    @Column(name = "business_justification", columnDefinition = "TEXT")
    private String businessJustification;

    @Column(name = "sla_id")
    private UUID slaId;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "response_due_date")
    private Instant responseDueDate;

    @Column(name = "resolution_due_date")
    private Instant resolutionDueDate;

    @Column(name = "first_response_at")
    private Instant firstResponseAt;

    @Column(name = "resolved_at")
    private Instant resolvedAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Column(name = "resolution", columnDefinition = "TEXT")
    private String resolution;

    @Column(name = "resolution_category", length = 100)
    private String resolutionCategory;

    @Column(name = "root_cause", columnDefinition = "TEXT")
    private String rootCause;

    @Column(name = "time_spent_minutes")
    private Integer timeSpentMinutes;

    @Column(name = "satisfaction_rating")
    private Integer satisfactionRating;

    @Column(name = "satisfaction_feedback", columnDefinition = "TEXT")
    private String satisfactionFeedback;


    @Column(name = "is_escalated")
    private Boolean isEscalated;

    @Column(name = "escalated_at")
    private Instant escalatedAt;

    @Column(name = "escalated_by_id")
    private UUID escalatedById;

    @Column(name = "escalation_reason", columnDefinition = "TEXT")
    private String escalationReason;

    @Column(name = "is_overdue")
    private Boolean isOverdue;

    @Column(name = "is_reopened")
    private Boolean isReopened;

    @Column(name = "reopen_count")
    private Integer reopenCount;

    @Column(name = "is_parent")
    private Boolean isParent;

    @Column(name = "parent_ticket_id")
    private UUID parentTicketId;

    @Column(name = "custom_fields", columnDefinition = "JSONB")
    private Object customFields;

    @Column(name = "tags", columnDefinition = "JSONB")
    private Object tags;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;
}
