package com.project.orderservice.userOrder.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserOrderRequest {

    private String itemId;

    private String userId;

    private int amount;

}
