package com.devteria_spring_boot.identity_service.service;

import com.devteria_spring_boot.identity_service.dto.Request.UserCreationRequest;
import com.devteria_spring_boot.identity_service.dto.Request.UserUpdateRequest;
import com.devteria_spring_boot.identity_service.entity.User;
import com.devteria_spring_boot.identity_service.exception.AppException;
import com.devteria_spring_boot.identity_service.exception.ErrorCode;
import com.devteria_spring_boot.identity_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreationRequest request) {
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
//! Builder annotation lombok
//        UserCreationRequest request1 = UserCreationRequest.builder()
//                .username("")
//                .firstName("")
//
//                .build();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
