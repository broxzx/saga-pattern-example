package com.project.orderservice.userOrder.service;

import com.project.orderservice.userOrder.data.UserOrder;
import com.project.orderservice.userOrder.data.dto.UserOrderRequest;
import com.project.orderservice.userOrder.data.dto.UserOrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, UserOrder> kafkaTemplate;

    public UserOrderResponse createUserOrder(UserOrderRequest userOrderRequest) {
        UserOrder userOrder = modelMapper.map(userOrderRequest, UserOrder.class);
        UserOrder savedUserOrder = userOrderRepository.save(userOrder);

        kafkaTemplate.send("user-order-created", userOrder.getId(), userOrder);

        return modelMapper.map(savedUserOrder, UserOrderResponse.class);
    }

}
