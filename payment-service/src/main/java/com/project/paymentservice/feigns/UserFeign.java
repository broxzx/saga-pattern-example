package com.project.paymentservice.feigns;

import com.project.paymentservice.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service", path = "/users")
public interface UserFeign {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") String userId);

    @PutMapping("/{id}/balance")
    void updateUserBalance(@PathVariable("id") String userId, @RequestBody long newBalance);

}
