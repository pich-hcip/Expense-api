// service/WalletService.java
package com.demo.Expense_api.service;

import com.demo.Expense_api.dto.wallet.WalletResponse;
import com.demo.Expense_api.entity.Wallet;
import com.demo.Expense_api.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public List<WalletResponse> getWalletsForUser(UUID userId) {
        return walletRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private WalletResponse toResponse(Wallet wallet) {
        return WalletResponse.builder()
                .id(wallet.getId())
                .name(wallet.getName())
                .currencyCode(wallet.getCurrencyCode())
                .balance(wallet.getBalance())
                .isDefault(wallet.isDefault())
                .build();
    }
}