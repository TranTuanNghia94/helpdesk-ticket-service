package com.it.ticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ticket_statuses")
public class StatusesEntity extends BaseEntity {
    
    @Column(name = "status_type")
    private String statusType;

    @Column(name = "color", length = 7)
    private String color;

    @Column(name = "icon", length = 100)
    private String icon;


    @Column(name = "is_initial")
    private Boolean isInitial;

    @Column(name = "is_final")
    private Boolean isFinal;

    @Column(name = "auto_close_hours")
    private Integer autoCloseHours;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "workflow_rules", columnDefinition = "JSONB")
    private Object workflowRules;

}
