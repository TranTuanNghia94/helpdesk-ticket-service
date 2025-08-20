package com.it.ticket.model.Ticket;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class TicketInfo {
    private UUID id;
    private String title;
    private String description;

    private String requesterName;
    private String requesterEmail;

    private String assignedTeamName;
    private String assignedToName;
    private Instant assignedAt;
    private UUID assignedByName;

    private UUID categoryId;
    private String categoryName;

    private UUID subcategoryId;
    private String subcategoryName;

    private UUID priorityId;
    private String priorityName;

    private UUID statusId;
    private String statusName;

    private String ticketType;
    private String source;

    private UUID affectedAssetId;
    private String affectedAssetName;

    private Object configurationItems;
    
    private String urgency;
    private String impact;
    private String businessJustification;

    private UUID slaId;
    private String slaName;

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
