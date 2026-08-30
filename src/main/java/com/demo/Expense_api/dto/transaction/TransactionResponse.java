// dto/transaction/TransactionResponse.java
package com.demo.Expense_api.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class TransactionResponse {
    private UUID id;
    private String kind;      // "EXPENSE" or "INCOME"
    private BigDecimal amount;
    private String title;
    private LocalDate transactionDate;
}