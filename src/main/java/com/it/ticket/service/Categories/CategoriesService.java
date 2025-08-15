package com.it.ticket.service.Categories;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.it.ticket.entity.CategoriesEntity;
import com.it.ticket.mapper.CategoryMapper;
import com.it.ticket.model.Categories.CategoryInfo;
import com.it.ticket.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriesService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryInfo> getAllCategories() {
        try {
            log.info("Getting all categories");
            List<CategoriesEntity> categories = categoryRepository.findAll();
            if (categories.isEmpty()) {
                log.info("No categories found");
                return List.of();
            }
            log.info("Categories found: {}", categories.size());
            return categoryMapper.toCategoryInfoList(categories);
        } catch (Exception e) {
            log.error("Error getting all categories", e);
            throw new RuntimeException("Error getting all categories", e);
        }
    }
}
