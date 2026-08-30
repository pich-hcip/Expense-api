// controller/DashboardController.java
package com.demo.Expense_api.controller;

import com.demo.Expense_api.dto.transaction.TransactionResponse;
import com.demo.Expense_api.dto.wallet.BalanceSummaryResponse;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import com.demo.Expense_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;

    @GetMapping("/summary")
    public ResponseEntity<BalanceSummaryResponse> getSummary(Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(transactionService.getBalanceSummary(user.getId()));
    }

    @GetMapping("/recent-transactions")
    public ResponseEntity<List<TransactionResponse>> getRecentTransactions(Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(transactionService.getRecentTransactions(user.getId()));
    }

    private User currentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}