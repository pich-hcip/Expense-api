package com.demo.Expense_api.controller;

import com.demo.Expense_api.dto.wallet.WalletRequest;
import com.demo.Expense_api.dto.wallet.WalletResponse;
import com.demo.Expense_api.entity.User;
import com.demo.Expense_api.repository.UserRepository;
import com.demo.Expense_api.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<WalletResponse>> getWallets(Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(walletService.getWalletsForUser(user.getId()));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(Authentication authentication) {
        User user = currentUser(authentication);
        BigDecimal total = walletService.getTotalBalance(user.getId());
        int count = walletService.getWalletCount(user.getId());
        return ResponseEntity.ok(Map.of("totalBalance", total, "walletCount", count));
    }

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(
            @Valid @RequestBody WalletRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(walletService.createWallet(user, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletResponse> updateWallet(
            @PathVariable UUID id,
            @Valid @RequestBody WalletRequest request,
            Authentication authentication) {
        User user = currentUser(authentication);
        return ResponseEntity.ok(walletService.updateWallet(user.getId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable UUID id, Authentication authentication) {
        User user = currentUser(authentication);
        walletService.deleteWallet(user.getId(), id);
        return ResponseEntity.noContent().build();
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}