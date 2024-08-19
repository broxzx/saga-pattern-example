package com.project.paymentservice.payments.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymentservice.feigns.InventoryFeign;
import com.project.paymentservice.feigns.UserFeign;
import com.project.paymentservice.model.ItemResponse;
import com.project.paymentservice.model.UserOrder;
import com.project.paymentservice.model.UserResponse;
import com.project.paymentservice.payments.data.Payment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @KafkaListener(topics = "user-order-created", groupId = "payment-service")
    public void getOrder(String userOrderModel) {
        UserOrder mappedUserOrder = objectMapper.readValue(userOrderModel, UserOrder.class);
        log.info("getOrder, {}", mappedUserOrder);

        UserResponse userResponse = userFeign.getUserById(mappedUserOrder.getUserId()).getBody();
        ItemResponse itemResponse = inventoryFeign.getItemById(mappedUserOrder.getItemId()).getBody();

        if (userResponse != null && itemResponse != null) {
            long priceUserToPay = calculateUserOrderRequest(mappedUserOrder, itemResponse);

            if (priceUserToPay > userResponse.getBalance() || mappedUserOrder.getAmount() > itemResponse.getAvailable()) {
                kafkaTemplate.send("user-order-cancelled", mappedUserOrder.getId());
                return;
            }

            log.info("mappedUserOrder.getId(): {}", mappedUserOrder.getId());
            kafkaTemplate.send("user-order-success", mappedUserOrder.getId());

            // proceed payment
            paymentRepository.save(Payment.builder()
                    .totalAmount(priceUserToPay)
                    .userId(userResponse.getId())
                    .transactionId(UUID.randomUUID().toString())
                    .build());

            userResponse.setBalance(userResponse.getBalance() - priceUserToPay);
            kafkaTemplate.send("update-user-balance", objectMapper.writeValueAsString(userResponse));
        }
    }

    private long calculateUserOrderRequest(UserOrder userOrder, ItemResponse itemResponse) {
        return userOrder.getAmount() * itemResponse.getAvailable();
    }

}
