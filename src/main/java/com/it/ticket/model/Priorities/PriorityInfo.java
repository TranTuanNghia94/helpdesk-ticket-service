package com.it.ticket.model.Priorities;

import lombok.Data;

@Data
public class PriorityInfo {
    private String id;
    private String name;
    private String code;
    private Integer level;
    private String color;
    private Integer responseTimeHours;
    private Integer resolutionTimeHours;
    private Boolean isActive;
}
