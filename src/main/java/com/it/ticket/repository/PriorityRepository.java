package com.it.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.it.ticket.entity.PrioritiesEntity;

public interface PriorityRepository extends JpaRepository<PrioritiesEntity, UUID> {

}
