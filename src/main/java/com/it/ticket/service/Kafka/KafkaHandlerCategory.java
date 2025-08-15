package com.it.ticket.service.Kafka;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.it.ticket.enums.Constant;
import com.it.ticket.model.Categories.CategoryInfo;
import com.it.ticket.model.Kafka.KafkaMessage;
import com.it.ticket.service.Categories.CategoriesService;
import com.it.ticket.utils.KafkaMessageBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaHandlerCategory {
    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;
    private final CategoriesService categoryService;

    private void sendMessage(KafkaMessage message, String topic) {
        log.info("Sending message to topic: {} | Message ID: {} | Status: {}", topic, message.getMessageId(),
                message.getStatus());
        kafkaTemplate.send(topic, message.getMessageId(), message);
    }

    private void sendErrorResponse(String messageId, String errorMessage) {
        KafkaMessage response = new KafkaMessage();
        response.setMessageId(messageId);
        response.setStatus(Constant.ResponseStatus.ERROR.getValue());
        response.setErrorMessage(errorMessage);
        response.setOperationType(Constant.OPERATION_GET_ALL_CATEGORIES);
        sendMessage(response, Constant.TICKET_EVENT_RESPONSE);
    }

    @KafkaListener(topics = Constant.TICKET_EVENT_REQUEST, groupId = Constant.EVENT_GROUP)
    public void listenCategoryEvent(KafkaMessage message) {
        try {
            if (message.getOperationType().equals(Constant.OPERATION_GET_ALL_CATEGORIES)) {
                List<CategoryInfo> categories = categoryService.getAllCategories();
                sendSuccessResponse(message.getMessageId(), categories);
            } else {
                log.error("Invalid operation type: {}", message.getOperationType());
                sendErrorResponse(message.getMessageId(), "Invalid operation type");
            }
        } catch (Exception e) {
            log.error("Error listening category event", e);
            sendErrorResponse(message.getMessageId(), e.getMessage());
        }
    }

    private void sendSuccessResponse(String messageId, List<CategoryInfo> categories) {
       log.info("Sending success response for message ID: {} | Categories: {}", messageId, categories.size());
       KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(Constant.OPERATION_GET_ALL_CATEGORIES, Constant.ResponseStatus.SUCCESS.getValue(), categories, messageId);
       sendMessage(response, Constant.TICKET_EVENT_RESPONSE);
    }
}
