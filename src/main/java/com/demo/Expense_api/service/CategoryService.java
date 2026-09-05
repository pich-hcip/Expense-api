// service/CategoryService.java
package com.demo.Expense_api.service;

import com.demo.Expense_api.dto.category.CategoryRequest;
import com.demo.Expense_api.dto.category.CategoryResponse;
import com.demo.Expense_api.entity.Category;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.CategoryRepository;
import com.demo.Expense_api.repository.TransactionRepository;
import com.demo.Expense_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public List<CategoryResponse> getCategories(UUID userId, String kindStr) {
        Category.Kind kind = Category.Kind.valueOf(kindStr.toUpperCase());
        return categoryRepository.findByKindAndUserIdOrUserIdIsNull(kind, userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategory(UUID userId, CategoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Category category = Category.builder()
                .user(user)
                .name(request.getName())
                .kind(Category.Kind.valueOf(request.getKind().toUpperCase()))
                .icon(request.getIcon())
                .colorHex(request.getColorHex())
                .isDefault(false)
                .build();

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    private CategoryResponse toResponse(Category category) {
        long count = transactionRepository.countByCategoryId(category.getId());
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .kind(category.getKind().name())
                .icon(category.getIcon())
                .colorHex(category.getColorHex())
                .transactionCount(count)
                .build();
    }
}