package com.project.orderservice.orders.model;

import com.project.orderservice.orders.data.dto.request.OrderRequestDto;
import com.project.orderservice.orders.data.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEvent {

    private UUID eventId = UUID.randomUUID();
    private LocalDateTime eventDate = LocalDateTime.now();
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }

}
