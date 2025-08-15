package com.it.ticket.model.Statuses;

import lombok.Data;

@Data
public class StatusInfo {
    private String id;
    private String name;
    private String code;
    private String statusType;
    private String color;
    private Boolean isInitial;
    private Boolean isFinal;
    private Integer sortOrder;
}
