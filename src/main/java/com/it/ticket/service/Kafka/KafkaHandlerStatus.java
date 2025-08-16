package com.it.ticket.service.Kafka;

import java.util.List;

import org.springframework.stereotype.Service;

import com.it.ticket.enums.Constant;
import com.it.ticket.model.Kafka.KafkaMessage;
import com.it.ticket.model.Statuses.StatusInfo;
import com.it.ticket.service.Statuses.StatusesService;
import com.it.ticket.utils.KafkaMessageBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaHandlerStatus {

    private final KafkaGeneral kafkaGeneral;
    private final StatusesService statusService;

    public void handleStatusEvent(KafkaMessage message) {
        try {
            List<StatusInfo> statuses = statusService.getAllStatuses();
            sendResponse(message.getMessageId(), statuses);
        } catch (Exception e) {
            log.error("Error handling status event for message ID: {}", message.getMessageId(), e);
            kafkaGeneral.sendErrorResponse(message.getMessageId(), Constant.OPERATION_GET_ALL_STATUSES, e.getMessage());
        }
    }

    private void sendResponse(String messageId, List<StatusInfo> statuses) {
        KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(
            Constant.OPERATION_GET_ALL_STATUSES, 
            Constant.ResponseStatus.SUCCESS.getValue(), 
            statuses, 
            messageId
        );
        kafkaGeneral.sendMessage(response);
    }
}
