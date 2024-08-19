package com.project.orderservice.orders.services;

import com.project.orderservice.orders.data.Order;
import com.project.orderservice.userOrder.data.UserOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public void createOrder(UserOrder userOrder) {
        log.info("createOrder: {}", userOrder);
        Order mappedOrder = modelMapper.map(userOrder, Order.class);
        mappedOrder.setOrderedAt(LocalDateTime.now());

        orderRepository.save(mappedOrder);
    }

}
