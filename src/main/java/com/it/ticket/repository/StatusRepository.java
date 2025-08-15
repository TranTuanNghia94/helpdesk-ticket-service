package com.it.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.it.ticket.entity.StatusesEntity;

public interface StatusRepository extends JpaRepository<StatusesEntity, UUID> {

}
