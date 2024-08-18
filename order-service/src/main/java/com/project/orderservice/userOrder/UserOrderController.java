package com.project.orderservice.userOrder;

import com.project.orderservice.userOrder.data.dto.UserOrderRequest;
import com.project.orderservice.userOrder.data.dto.UserOrderResponse;
import com.project.orderservice.userOrder.service.UserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-orders")
@RequiredArgsConstructor
public class UserOrderController {

    private final UserOrderService userOrderService;

    @PostMapping
    public ResponseEntity<UserOrderResponse> createUserOrder(@RequestBody UserOrderRequest userOrderRequest) {
        return ResponseEntity.ok(userOrderService.createUserOrder(userOrderRequest));
    }

}
