package com.it.ticket.service.Kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.it.ticket.enums.Constant;
import com.it.ticket.model.Kafka.KafkaMessage;
import com.it.ticket.utils.KafkaMessageBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaGeneral {
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendMessage(KafkaMessage message) {
        log.info("Sending message to operation: {} | Message ID: {} | Status: {}", message.getOperationType(), message.getMessageId(), message.getStatus());
        kafkaTemplate.send(Constant.TICKET_EVENT_RESPONSE, message.getMessageId(), message);
    }

    public void sendErrorResponse(String messageId, String operationType, String errorMessage) {
        KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(
            operationType, 
            Constant.ResponseStatus.ERROR.getValue(), 
            errorMessage, 
            messageId
        );
        sendMessage(response);
    }
}
