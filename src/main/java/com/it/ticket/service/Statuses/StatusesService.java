package com.it.ticket.service.Statuses;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.it.ticket.entity.StatusesEntity;
import com.it.ticket.mapper.StatusMapper;
import com.it.ticket.model.Statuses.StatusInfo;
import com.it.ticket.repository.StatusRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusesService {
    private final StatusRepository statusRepo;
    private final StatusMapper statusMapper;

    public List<StatusInfo> getAllStatuses() {
        try {
            log.info("Getting all statuses");
            List<StatusesEntity> statuses = statusRepo.findAll();
            if (statuses.isEmpty()) {
                log.info("No statuses found");
                return List.of();
            }
            log.info("Statuses found: {}", statuses.size());

            return statusMapper.toStatusInfoList(statuses);
        } catch (Exception e) {
            log.error("Error getting all statuses", e);
            throw new RuntimeException("Error getting all statuses", e);
        }
    }
}
