package com.demo.Expense_api.dto.transaction;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    @NotBlank
    private String walletId;

    private String categoryId; // optional

    @NotBlank
    private String kind; // "EXPENSE" or "INCOME"

    @NotNull @Positive
    private BigDecimal amount;

    private String title;

    private LocalDate transactionDate;
}