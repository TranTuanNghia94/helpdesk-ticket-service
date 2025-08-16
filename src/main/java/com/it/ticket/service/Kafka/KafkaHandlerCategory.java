package com.it.ticket.service.Kafka;

import java.util.List;

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

    public void handleCategoryEvent(KafkaMessage message) {
        try {
            List<CategoryInfo> categories = categoryService.getAllCategories();
            sendResponse(message.getMessageId(), categories);
        } catch (Exception e) {
            log.error("Error handling category event for message ID: {}", message.getMessageId(), e);
            sendErrorResponse(message.getMessageId(), e.getMessage());
        }
    }

    private void sendResponse(String messageId, List<CategoryInfo> categories) {
        KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(
            Constant.OPERATION_GET_ALL_CATEGORIES, 
            Constant.ResponseStatus.SUCCESS.getValue(), 
            categories, 
            messageId
        );
        sendMessage(response);
    }

    private void sendErrorResponse(String messageId, String errorMessage) {
        KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(
            Constant.OPERATION_GET_ALL_CATEGORIES, 
            Constant.ResponseStatus.ERROR.getValue(), 
            errorMessage, 
            messageId
        );
        sendMessage(response);
    }

    private void sendMessage(KafkaMessage message) {
        log.info("Sending message to topic: {} | Message ID: {} | Status: {}", 
            Constant.TICKET_EVENT_RESPONSE, message.getMessageId(), message.getStatus());
        kafkaTemplate.send(Constant.TICKET_EVENT_RESPONSE, message.getMessageId(), message);
    }
}
