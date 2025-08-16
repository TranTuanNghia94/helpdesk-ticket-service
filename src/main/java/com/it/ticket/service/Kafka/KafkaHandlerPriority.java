package com.it.ticket.service.Kafka;

import java.util.List;

import org.springframework.stereotype.Service;

import com.it.ticket.enums.Constant;
import com.it.ticket.model.Kafka.KafkaMessage;
import com.it.ticket.model.Priorities.PriorityInfo;
import com.it.ticket.service.Priorities.PrioritiesService;
import com.it.ticket.utils.KafkaMessageBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaHandlerPriority {

    private final KafkaGeneral kafkaGeneral;
    private final PrioritiesService prioritiesService;

    public void handlePriorityEvent(KafkaMessage message) {
        try {
            List<PriorityInfo> priorities = prioritiesService.getAllPriorities();
            sendResponse(message.getMessageId(), priorities);
        } catch (Exception e) {
            log.error("Error handling priority event for message ID: {}", message.getMessageId(), e);
            kafkaGeneral.sendErrorResponse(message.getMessageId(), Constant.OPERATION_GET_ALL_PRIORITIES, e.getMessage());
        }
    }


    private void sendResponse(String messageId, List<PriorityInfo> priorities) {
        KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(
            Constant.OPERATION_GET_ALL_PRIORITIES, 
            Constant.ResponseStatus.SUCCESS.getValue(), 
            priorities, 
            messageId
        );
        kafkaGeneral.sendMessage(response);
    }

}
