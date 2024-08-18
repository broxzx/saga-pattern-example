package com.project.orderservice.userOrder.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user-orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserOrder {

    private String id;

    private String itemId;

    private String userId;

    private int amount;

}
