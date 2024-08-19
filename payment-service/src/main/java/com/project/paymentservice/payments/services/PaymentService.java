package com.project.paymentservice.payments.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymentservice.model.UserOrder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(topics = "user-order-created", groupId = "payment-service")
    public void getOrder(String userOrderModel) {
        UserOrder mappedUserOrder = objectMapper.readValue(userOrderModel, UserOrder.class);
        log.info("getOrder, {}", mappedUserOrder.toString());
    }

}
