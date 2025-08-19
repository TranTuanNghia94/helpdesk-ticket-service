package com.it.ticket.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.it.ticket.entity.TicketsEntity;
import com.it.ticket.model.Ticket.CreateTicket;

@Component
public class TicketMapper {

    public TicketsEntity toEntity(CreateTicket createTicket) {
        TicketsEntity ticket = new TicketsEntity(); 

        // organization
        ticket.setOrganizationId(createTicket.getOrganizationId());
        ticket.setTitle(createTicket.getTitle());
        ticket.setDescription(createTicket.getDescription());

        // requester
        ticket.setRequesterId(createTicket.getRequesterId());
        ticket.setRequesterEmail(createTicket.getRequesterEmail());
        ticket.setRequesterPhone(createTicket.getRequesterPhone());

        // assigned
        ticket.setAssignedToId(createTicket.getAssignedToId());
        ticket.setAssignedTeamId(createTicket.getAssignedTeamId());
        ticket.setAssignedAt(createTicket.getAssignedAt());
        ticket.setAssignedById(createTicket.getAssignedById());

        // category
        ticket.setCategoryId(createTicket.getCategoryId());
        ticket.setSubcategoryId(createTicket.getSubcategoryId());

        // priority
        ticket.setPriorityId(createTicket.getPriorityId());
        
        // status
        ticket.setStatusId(createTicket.getStatusId());

        // ticket type
        ticket.setTicketType(createTicket.getTicketType());

        // source
        ticket.setSource(createTicket.getSource());
        
        // asset
        ticket.setAffectedAssetId(createTicket.getAffectedAssetId());

        // configuration items
        ticket.setConfigurationItems(createTicket.getConfigurationItems());

        // urgency
        ticket.setUrgency(createTicket.getUrgency());

        // impact
        ticket.setImpact(createTicket.getImpact());

        // business justification
        ticket.setBusinessJustification(createTicket.getBusinessJustification());

        // sla
        ticket.setSlaId(createTicket.getSlaId());

        // due date
        ticket.setDueDate(createTicket.getDueDate());

        // response due date
        ticket.setResponseDueDate(createTicket.getResponseDueDate());

        // resolution due date
        ticket.setResolutionDueDate(createTicket.getResolutionDueDate());

        // first response at
        ticket.setFirstResponseAt(createTicket.getFirstResponseAt());

        // resolved at
        ticket.setResolvedAt(createTicket.getResolvedAt());

        // closed at
        ticket.setClosedAt(createTicket.getClosedAt());

        // resolution
        ticket.setResolution(createTicket.getResolution());
        ticket.setResolutionCategory(createTicket.getResolutionCategory());

        // root cause
        ticket.setRootCause(createTicket.getRootCause());

        // time spent minutes
        ticket.setTimeSpentMinutes(createTicket.getTimeSpentMinutes());

        // satisfaction rating
        ticket.setSatisfactionRating(createTicket.getSatisfactionRating());

        // satisfaction feedback
        ticket.setSatisfactionFeedback(createTicket.getSatisfactionFeedback());

        // escalated
        ticket.setIsEscalated(createTicket.getIsEscalated());
        ticket.setEscalatedAt(createTicket.getEscalatedAt());
        ticket.setEscalatedById(createTicket.getEscalatedById());
        ticket.setEscalationReason(createTicket.getEscalationReason());

        // overdue
        ticket.setIsOverdue(createTicket.getIsOverdue());

        // reopened
        ticket.setIsReopened(createTicket.getIsReopened());
        ticket.setReopenCount(createTicket.getReopenCount());

        // parent ticket
        ticket.setIsParent(createTicket.getIsParent());
        ticket.setParentTicketId(createTicket.getParentTicketId());

        // custom fields
        ticket.setCustomFields(createTicket.getCustomFields());

        // tags
        ticket.setTags(createTicket.getTags());


        ticket.setCreatedAt(createTicket.getCreatedAt());
        ticket.setUpdatedAt(createTicket.getUpdatedAt());
        ticket.setCreatedBy(createTicket.getCreatedBy());
        ticket.setUpdatedBy(createTicket.getUpdatedBy());

        return ticket;
    }
}
