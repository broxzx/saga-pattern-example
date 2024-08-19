package com.project.userservice.users.services;

import com.project.userservice.users.data.User;
import com.project.userservice.users.data.dto.UserRequest;
import com.project.userservice.users.data.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponse saveUser(UserRequest userRequest) {
        User mappedUser = modelMapper.map(userRequest, User.class);
        User savedUser = userRepository.save(mappedUser);

        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse getUserById(String userId) {
        User obtainedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user with id '%s' is not found".formatted(userId)));

        return modelMapper.map(obtainedUser, UserResponse.class);
    }
}
