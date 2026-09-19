// dto/budget/BudgetResponse.java
package com.demo.Expense_api.dto.budget;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class BudgetResponse {
    private UUID id;
    private String categoryId;
    private String categoryName;
    private String categoryIcon;
    private String categoryColorHex;
    private BigDecimal monthlyLimit;
    private BigDecimal spentSoFar;
    private BigDecimal remaining;
    private double percentUsed;
}