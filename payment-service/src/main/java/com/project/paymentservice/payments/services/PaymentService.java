package com.project.paymentservice.payments.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymentservice.feigns.InventoryFeign;
import com.project.paymentservice.feigns.UserFeign;
import com.project.paymentservice.model.ItemResponse;
import com.project.paymentservice.model.UserOrder;
import com.project.paymentservice.model.UserResponse;
import com.project.paymentservice.payments.data.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final ObjectMapper objectMapper;
    private final UserFeign userFeign;
    private final InventoryFeign inventoryFeign;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PaymentRepository paymentRepository;

    @KafkaListener(topics = "user-order-created", groupId = "payment-service")
    public void processOrder(String userOrderModel) {
        try {
            UserOrder userOrder = objectMapper.readValue(userOrderModel, UserOrder.class);
            handleUserOrder(userOrder);
        } catch (Exception e) {
            log.error("Failed to process user order: {}", e.getMessage(), e);
        }
    }

    private void handleUserOrder(UserOrder userOrder) {
        UserResponse userResponse = getUserResponse(userOrder.getUserId());
        ItemResponse itemResponse = getItemResponse(userOrder.getItemId());

        if (userResponse != null && itemResponse != null) {
            long priceToPay = calculatePrice(userOrder, itemResponse);

            if (!canUserPay(userResponse, priceToPay) || !isItemAvailable(userOrder, itemResponse)) {
                sendOrderCancelled(userOrder);
            } else {
                completeOrder(userOrder, userResponse, itemResponse, priceToPay);
            }
        } else {
            log.warn("Failed to retrieve user or item data for order: {}", userOrder.getId());
        }
    }

    private UserResponse getUserResponse(String userId) {
        try {
            return userFeign.getUserById(userId).getBody();
        } catch (Exception e) {
            log.error("Failed to retrieve user data for userId {}: {}", userId, e.getMessage());
            return null;
        }
    }

    private ItemResponse getItemResponse(String itemId) {
        try {
            return inventoryFeign.getItemById(itemId).getBody();
        } catch (Exception e) {
            log.error("Failed to retrieve item data for itemId {}: {}", itemId, e.getMessage());
            return null;
        }
    }

    private long calculatePrice(UserOrder userOrder, ItemResponse itemResponse) {
        return userOrder.getAmount() * itemResponse.getPrice();
    }

    private boolean canUserPay(UserResponse userResponse, long priceToPay) {
        return userResponse.getBalance() >= priceToPay;
    }

    private boolean isItemAvailable(UserOrder userOrder, ItemResponse itemResponse) {
        return userOrder.getAmount() <= itemResponse.getAvailable();
    }

    private void sendOrderCancelled(UserOrder userOrder) {
        sendKafkaMessage("user-order-cancelled", userOrder.getId());
        log.info("Order cancelled for userOrderId {}", userOrder.getId());
    }

    private void completeOrder(UserOrder userOrder, UserResponse userResponse, ItemResponse itemResponse, long priceToPay) {
        sendKafkaMessage("user-order-success", userOrder.getId());

        // Mocked proceeded payment
        paymentRepository.save(Payment.builder()
                .totalAmount(priceToPay)
                .userId(userResponse.getId())
                .transactionId(UUID.randomUUID().toString())
                .build());

        userResponse.setBalance(userResponse.getBalance() - priceToPay);
        itemResponse.setAvailable(itemResponse.getAvailable() - userOrder.getAmount());

        updateUserBalance(userResponse);
        updateItemAvailability(itemResponse);
    }

    private void updateUserBalance(UserResponse userResponse) {
        try {
            userFeign.updateUserBalance(userResponse.getId(), userResponse.getBalance());
        } catch (Exception e) {
            log.error("Failed to update user balance for userId {}: {}", userResponse.getId(), e.getMessage());
        }
    }

    private void updateItemAvailability(ItemResponse itemResponse) {
        try {
            inventoryFeign.updateItemAmount(itemResponse.getId(), itemResponse.getAvailable());
        } catch (Exception e) {
            log.error("Failed to update item availability for itemId {}: {}", itemResponse.getId(), e.getMessage());
        }
    }

    private void sendKafkaMessage(String topic, String messageId) {
        kafkaTemplate.send(topic, messageId).whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Message sent to topic '{}' with partition '{}', messageId '{}'",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        messageId);
            } else {
                log.error("Failed to send message to topic '{}', messageId '{}', error: {}",
                        topic, messageId, exception.getMessage());
            }
        });
    }

}
