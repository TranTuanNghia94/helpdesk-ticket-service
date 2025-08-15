package com.it.ticket.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "priorities")
public class PrioritiesEntity extends BaseEntity {
    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "color", length = 7)
    private String color;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "response_time_hours")
    private Integer responseTimeHours;

    @Column(name = "resolution_time_hours")
    private Integer resolutionTimeHours;

    @Column(name = "auto_escalate")
    private Boolean autoEscalate;

    @Column(name = "escalation_time_hours")
    private Integer escalationTimeHours;
}
