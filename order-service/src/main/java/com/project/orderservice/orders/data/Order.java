package com.project.orderservice.orders.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    private String id;

    private String productId;

    private String userId;

    private int amount;

}
