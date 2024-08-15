package com.project.orderservice.orders.data.dto.response;

import com.project.orderservice.orders.data.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderResponseDto {

    private String orderId;

    private String productId;

    private String userId;

    private int amount;

    private OrderStatus orderStatus;

}
