package com.it.ticket.model.Categories;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoryInfo {
    private UUID id;
    private String name;
    private String code;
    private String description;
    private Boolean isActive;
    private Integer sortOrder;
}
