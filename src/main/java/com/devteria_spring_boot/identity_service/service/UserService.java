package com.devteria_spring_boot.identity_service.service;

import com.devteria_spring_boot.identity_service.dto.request.UserCreationRequest;
import com.devteria_spring_boot.identity_service.dto.request.UserUpdateRequest;
import com.devteria_spring_boot.identity_service.dto.response.UserResponse;
import com.devteria_spring_boot.identity_service.entity.User;
import com.devteria_spring_boot.identity_service.exception.AppException;
import com.devteria_spring_boot.identity_service.exception.ErrorCode;
import com.devteria_spring_boot.identity_service.mapper.UserMapper;
import com.devteria_spring_boot.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

//! Builder annotation lombok
//        UserCreationRequest request1 = UserCreationRequest.builder()
//                .username("")
//                .firstName("")
//                .build();

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
