package com.project.userservice.users;

import com.project.userservice.users.data.dto.UserRequest;
import com.project.userservice.users.data.dto.UserResponse;
import com.project.userservice.users.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.saveUser(userRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.getUserResponseById(userId));
    }

    @PutMapping("/{id}/balance")
    public void updateUserBalance(@PathVariable("id") String userId, @RequestBody long newBalance) {
        userService.updateUserBalance(userId, newBalance);
    }


}
