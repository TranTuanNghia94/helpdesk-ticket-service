package com.it.ticket.service.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.it.ticket.enums.Constant;
import com.it.ticket.model.Kafka.KafkaMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaGeneralHandler {

    private final KafkaHandlerCategory kafkaHandlerCategory;

    @KafkaListener(topics = Constant.TICKET_EVENT_REQUEST, groupId = Constant.EVENT_GROUP)
    public void listenCategoryEvent(KafkaMessage message) {
        try {
            if (Constant.OPERATION_GET_ALL_CATEGORIES.equals(message.getOperationType())) {
                kafkaHandlerCategory.handleCategoryEvent(message);
            } else {
                log.warn("Unsupported operation type: {}", message.getOperationType());
            }
        } catch (Exception e) {
            log.error("Error processing category event for message ID: {}", message.getMessageId(), e);
        }
    }
}
