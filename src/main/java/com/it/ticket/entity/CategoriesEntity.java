package com.it.ticket.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class CategoriesEntity extends BaseEntity {
    @Column(name = "parent_category_id")
    private UUID parentCategoryId;

    @Column(name = "organization_id")
    private UUID organizationId;


    @Column(name = "icon", length = 100)
    private String icon;
    
    @Column(name = "color", length = 7)
    private String color;
    
    @Column(name = "sort_order")
    private Integer sortOrder;
    
    @Column(name = "auto_assignment_rules", columnDefinition = "JSONB")
    private Object autoAssignmentRules;
    
    @Column(name = "escalation_rules", columnDefinition = "JSONB")
    private Object escalationRules;
    
    @Column(name = "sla_id")
    private UUID slaId;
}
