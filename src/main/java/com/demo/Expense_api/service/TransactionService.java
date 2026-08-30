// service/TransactionService.java
package com.demo.Expense_api.service;

import com.demo.Expense_api.dto.transaction.TransactionResponse;
import com.demo.Expense_api.dto.wallet.BalanceSummaryResponse;
import com.demo.Expense_api.entity.Transaction;
import com.demo.Expense_api.repository.TransactionRepository;
import com.demo.Expense_api.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public List<TransactionResponse> getRecentTransactions(UUID userId) {
        return transactionRepository
                .findTop10ByUserIdOrderByTransactionDateDescCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BalanceSummaryResponse getBalanceSummary(UUID userId) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        return BalanceSummaryResponse.builder()
                .totalBalance(walletRepository.sumBalanceByUserId(userId))
                .totalIncome(transactionRepository.sumIncomeBetween(userId, start, end))
                .totalExpense(transactionRepository.sumExpenseBetween(userId, start, end))
                .build();
    }

    private TransactionResponse toResponse(Transaction t) {
        return TransactionResponse.builder()
                .id(t.getId())
                .kind(t.getKind().name())
                .amount(t.getAmount())
                .title(t.getTitle())
                .transactionDate(t.getTransactionDate())
                .build();
    }
}