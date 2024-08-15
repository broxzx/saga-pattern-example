package com.project.orderservice.orders.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequestDto {

    private String orderId;

    private String productId;

    private String userId;

    private int amount;

}
