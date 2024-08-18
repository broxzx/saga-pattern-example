package com.project.orderservice.userOrder.service;

import com.project.orderservice.userOrder.data.UserOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserOrderRepository extends MongoRepository<UserOrder, String> {



}
