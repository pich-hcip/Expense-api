// controller/CategoryController.java
package com.demo.Expense_api.controller;

import com.demo.Expense_api.dto.category.CategoryRequest;
import com.demo.Expense_api.dto.category.CategoryResponse;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import com.demo.Expense_api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @RequestParam String kind,
            Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(categoryService.getCategories(user.getId(), kind));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(categoryService.createCategory(user.getId(), request));
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}