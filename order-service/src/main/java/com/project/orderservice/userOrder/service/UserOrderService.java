package com.project.orderservice.userOrder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.orderservice.userOrder.data.UserOrder;
import com.project.orderservice.userOrder.data.dto.UserOrderRequest;
import com.project.orderservice.userOrder.data.dto.UserOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public UserOrderResponse createUserOrder(UserOrderRequest userOrderRequest) {
        UserOrder userOrder = modelMapper.map(userOrderRequest, UserOrder.class);
        UserOrder savedUserOrder = userOrderRepository.save(userOrder);

        log.info(savedUserOrder.toString());
        kafkaTemplate.send("user-order-created", objectMapper.writeValueAsString(savedUserOrder)).whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Sent message: {} to topic: '{}' and offset: '{}'", savedUserOrder, result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message: '{}', exception occurred: '{}'", savedUserOrder, exception.getMessage());
            }
        });

        return modelMapper.map(savedUserOrder, UserOrderResponse.class);
    }

}
