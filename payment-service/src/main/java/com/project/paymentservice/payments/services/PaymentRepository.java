package com.project.paymentservice.payments.services;

import com.project.paymentservice.payments.data.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {



}
