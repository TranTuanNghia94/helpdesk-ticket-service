package com.it.ticket.service.Kafka;

import java.util.List;

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
    
    private final KafkaGeneral kafkaGeneral;
    private final CategoriesService categoryService;

    public void handleCategoryEvent(KafkaMessage message) {
        try {
            List<CategoryInfo> categories = categoryService.getAllCategories();
            sendResponse(message.getMessageId(), categories);
        } catch (Exception e) {
            log.error("Error handling category event for message ID: {}", message.getMessageId(), e);
            kafkaGeneral.sendErrorResponse(message.getMessageId(), Constant.OPERATION_GET_ALL_CATEGORIES, e.getMessage());
        }
    }

    private void sendResponse(String messageId, List<CategoryInfo> categories) {
        KafkaMessage response = KafkaMessageBuilder.buildKafkaMessage(
            Constant.OPERATION_GET_ALL_CATEGORIES, 
            Constant.ResponseStatus.SUCCESS.getValue(), 
            categories, 
            messageId
        );
        kafkaGeneral.sendMessage(response);
    }
}
