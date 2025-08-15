package com.it.ticket.model.Categories;

import lombok.Data;

@Data
public class CategoryInfo {
    private String id;
    private String name;
    private String code;
    private String description;
    private Boolean isActive;
    private Integer sortOrder;
}
