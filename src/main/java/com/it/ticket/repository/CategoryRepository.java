package com.it.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.it.ticket.entity.CategoriesEntity;

public interface CategoryRepository extends JpaRepository<CategoriesEntity, UUID> {

}
