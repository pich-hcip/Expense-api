package com.demo.Expense_api.controller;

import com.demo.Expense_api.dto.transaction.TransactionRequest;
import com.demo.Expense_api.dto.transaction.TransactionResponse;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import com.demo.Expense_api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(transactionService.createTransaction(user, request));
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}