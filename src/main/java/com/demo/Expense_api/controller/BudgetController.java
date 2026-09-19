package com.demo.Expense_api.controller;

import com.demo.Expense_api.dto.budget.BudgetRequest;
import com.demo.Expense_api.dto.budget.BudgetResponse;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import com.demo.Expense_api.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getBudgets(Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(budgetService.getCurrentMonthBudgets(user.getId()));
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> saveBudget(
            @Valid @RequestBody BudgetRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(budgetService.createOrUpdateBudget(user, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id, Authentication authentication) {
        User user = currentUser(authentication);
        budgetService.deleteBudget(user.getId(), id);
        return ResponseEntity.noContent().build();
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}