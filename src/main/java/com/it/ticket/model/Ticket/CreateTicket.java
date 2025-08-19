package com.it.ticket.model.Ticket;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class CreateTicket {
    private UUID organizationId;
    private String title;
    private String description;

    private UUID requesterId;
    private String requesterEmail;
    private String requesterPhone;

    private UUID assignedToId;
    private UUID assignedTeamId;
    private Instant assignedAt;
    private UUID assignedById;

    private UUID categoryId;
    private UUID subcategoryId;
    private UUID priorityId;
    private UUID statusId;
    private String ticketType;
    private String source;

    private UUID affectedAssetId;
    private Object configurationItems;
    private String urgency;
    private String impact;
    private String businessJustification;

    private UUID slaId;
    private Instant dueDate;
    private Instant responseDueDate;
    private Instant resolutionDueDate;
    private Instant firstResponseAt;
    private Instant resolvedAt;
    private Instant closedAt;

    private String resolution;
    private String resolutionCategory;
    private String rootCause;

    private Integer timeSpentMinutes;
    private Integer satisfactionRating;
    private String satisfactionFeedback;

    private Boolean isEscalated;
    private Instant escalatedAt;
    private UUID escalatedById;
    private String escalationReason;

    private Boolean isOverdue;
    private Boolean isReopened;
    private Integer reopenCount;
    private Boolean isParent;
    private UUID parentTicketId;
    private Object customFields;
    private Object tags;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
