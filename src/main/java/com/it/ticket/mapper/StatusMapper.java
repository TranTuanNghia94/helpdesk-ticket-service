package com.it.ticket.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.it.ticket.entity.StatusesEntity;
import com.it.ticket.model.Statuses.StatusInfo;

@Component
public class StatusMapper {
    public StatusInfo toStatusInfo(StatusesEntity status) {
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setId(status.getId());
        statusInfo.setName(status.getName());
        statusInfo.setCode(status.getCode());
        statusInfo.setStatusType(status.getStatusType());
        statusInfo.setColor(status.getColor());
        statusInfo.setIsInitial(status.getIsInitial());
        statusInfo.setIsFinal(status.getIsFinal());
        statusInfo.setSortOrder(status.getSortOrder());
        return statusInfo;
    }

    public List<StatusInfo> toStatusInfoList(List<StatusesEntity> statuses) {
        return statuses.stream()
            .map(this::toStatusInfo)
            .collect(Collectors.toList());
    }
}
