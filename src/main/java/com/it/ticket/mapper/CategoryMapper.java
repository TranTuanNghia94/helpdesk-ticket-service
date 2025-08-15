package com.it.ticket.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.it.ticket.entity.CategoriesEntity;
import com.it.ticket.model.Categories.CategoryInfo;

@Component
public class CategoryMapper {
    
    public CategoryInfo toCategoryInfo(CategoriesEntity category) {
        CategoryInfo categoryInfo = new CategoryInfo();
        categoryInfo.setId(category.getId());
        categoryInfo.setName(category.getName());
        categoryInfo.setCode(category.getCode());
        categoryInfo.setDescription(category.getDescription());
        categoryInfo.setIsActive(category.getIsActive());
        categoryInfo.setSortOrder(category.getSortOrder());
        return categoryInfo;
    }

    public List<CategoryInfo> toCategoryInfoList(List<CategoriesEntity> categories) {
        return categories.stream()
            .map(this::toCategoryInfo)
            .collect(Collectors.toList());
    }
}
