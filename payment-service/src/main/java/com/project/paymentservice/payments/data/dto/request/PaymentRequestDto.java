package com.project.paymentservice.payments.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentRequestDto {

    private String orderId;

    private String userId;

    private int amount;

}
