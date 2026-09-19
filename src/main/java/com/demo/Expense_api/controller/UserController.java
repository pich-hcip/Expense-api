package com.demo.Expense_api.controller;

import com.demo.Expense_api.dto.auth.UpdateAvatarRequest;
import com.demo.Expense_api.dto.auth.UpdateProfileRequest;
import com.demo.Expense_api.dto.auth.UserProfileResponse;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import com.demo.Expense_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        User user = currentUser(authentication);

        return ResponseEntity.ok(UserProfileResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatarUrl(user.getAvatarUrl())
                .defaultCurrency(user.getDefaultCurrency())
                .build());
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        userService.updateProfile(user, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/avatar")
    public ResponseEntity<Void> updateAvatar(
            @Valid @RequestBody UpdateAvatarRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        userService.updateAvatarUrl(user, request.getAvatarUrl());
        return ResponseEntity.ok().build();
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}