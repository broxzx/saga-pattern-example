package com.project.userservice.users.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.userservice.users.data.User;
import com.project.userservice.users.data.dto.UserRequest;
import com.project.userservice.users.data.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    public UserResponse saveUser(UserRequest userRequest) {
        User mappedUser = modelMapper.map(userRequest, User.class);
        User savedUser = userRepository.save(mappedUser);

        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse getUserResponseById(String userId) {
        User obtainedUser = getUserById(userId);

        return modelMapper.map(obtainedUser, UserResponse.class);
    }

    @SneakyThrows
    @KafkaListener(topics = "update-user-balance", groupId = "user-service")
    public void updateUserBalance(String userModel) {
        log.info(userModel);
        User user = objectMapper.readValue(userModel, User.class);
        User obtainedUser = getUserById(user.getId());

        obtainedUser.setBalance(user.getBalance());
        userRepository.save(obtainedUser);
    }

    public void updateUserBalance(String userId, long newBalance) {
        log.info("updateUserBalance: {}, newBalance: {}", userId, newBalance);
        User obtainedUser = getUserById(userId);

        obtainedUser.setBalance(newBalance);
        userRepository.save(obtainedUser);
    }


    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user with id '%s' is not found".formatted(userId)));
    }
}
