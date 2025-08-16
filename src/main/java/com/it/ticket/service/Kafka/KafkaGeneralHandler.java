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
    private final KafkaHandlerPriority kafkaHandlerPriority;
    private final KafkaHandlerStatus kafkaHandlerStatus;
    private final KafkaGeneral kafkaGeneral;
    
    @KafkaListener(topics = Constant.TICKET_EVENT_REQUEST, groupId = Constant.EVENT_GROUP)
    public void listenCategoryEvent(KafkaMessage message) {
        try {
            switch (message.getOperationType()) {
                case Constant.OPERATION_GET_ALL_CATEGORIES:
                    kafkaHandlerCategory.handleCategoryEvent(message);
                    break;
                case Constant.OPERATION_GET_ALL_PRIORITIES:
                    kafkaHandlerPriority.handlePriorityEvent(message);
                    break;
                case Constant.OPERATION_GET_ALL_STATUSES:
                    kafkaHandlerStatus.handleStatusEvent(message);
                    break;
                default:
                    log.warn("Unsupported operation type: {}", message.getOperationType());
                    kafkaGeneral.sendErrorResponse(message.getMessageId(), message.getOperationType(), "Unsupported operation type");
                    break;
            }
        } catch (Exception e) {
            log.error("Error processing category event for message ID: {}", message.getMessageId(), e);
        }
    }
}
