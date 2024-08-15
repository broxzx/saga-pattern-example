package com.project.paymentservice.model;

import com.project.paymentservice.payments.data.dto.request.PaymentRequestDto;
import com.project.paymentservice.payments.data.enums.PaymentStatus;
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
public class PaymentEvent {

    private UUID eventId = UUID.randomUUID();
    private LocalDateTime eventDate = LocalDateTime.now();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }

}
