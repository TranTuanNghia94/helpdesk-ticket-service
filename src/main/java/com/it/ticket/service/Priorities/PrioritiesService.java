package com.it.ticket.service.Priorities;

import java.util.List;

import org.springframework.stereotype.Service;

import com.it.ticket.entity.PrioritiesEntity;
import com.it.ticket.mapper.PriorityMapper;
import com.it.ticket.model.Priorities.PriorityInfo;
import com.it.ticket.repository.PriorityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrioritiesService {    
    private final PriorityRepository priorityRepo;
    private final PriorityMapper priorityMapper;

    public List<PriorityInfo> getAllPriorities() {
        try {
            log.info("Getting all priorities");
            List<PrioritiesEntity> priorities = priorityRepo.findAll();
            if (priorities.isEmpty()) {
                log.info("No priorities found");
                return List.of();
            }
            log.info("Priorities found: {}", priorities.size());
            return priorityMapper.toPriorityInfoList(priorities);
        } catch (Exception e) {
            log.error("Error getting all priorities", e);
            throw new RuntimeException("Error getting all priorities", e);
        }
    }
}
