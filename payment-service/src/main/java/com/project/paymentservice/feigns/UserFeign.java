package com.project.paymentservice.feigns;

import com.project.paymentservice.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", path = "/users")
public interface UserFeign {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") String userId);

}
