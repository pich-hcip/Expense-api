package com.demo.Expense_api.service;

import com.demo.Expense_api.dto.auth.UpdateProfileRequest;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateProfile(User user, UpdateProfileRequest request) {
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
    }

    @Transactional
    public void updateAvatarUrl(User user, String avatarUrl) {
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
    }
}