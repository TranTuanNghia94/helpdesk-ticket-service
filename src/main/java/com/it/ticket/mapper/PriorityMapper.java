package com.it.ticket.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.it.ticket.entity.PrioritiesEntity;
import com.it.ticket.model.Priorities.PriorityInfo;

@Component
public class PriorityMapper {
    public PriorityInfo toPriorityInfo(PrioritiesEntity priority) {
        PriorityInfo priorityInfo = new PriorityInfo();
        priorityInfo.setId(priority.getId());
        priorityInfo.setName(priority.getName());
        priorityInfo.setCode(priority.getCode());
        priorityInfo.setLevel(priority.getLevel());
        priorityInfo.setDescription(priority.getDescription());
        priorityInfo.setColor(priority.getColor());
        priorityInfo.setResponseTimeHours(priority.getResponseTimeHours());
        priorityInfo.setResolutionTimeHours(priority.getResolutionTimeHours()); 
        priorityInfo.setIsActive(priority.getIsActive());
        return priorityInfo;
    }


    public List<PriorityInfo> toPriorityInfoList(List<PrioritiesEntity> priorities) {
        return priorities.stream()
            .map(this::toPriorityInfo)
            .collect(Collectors.toList());
    }
}
