package com.project.paymentservice.payments.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {

    private String id;

    private String userId;

    private long totalAmount;

    private String transactionId;

}
