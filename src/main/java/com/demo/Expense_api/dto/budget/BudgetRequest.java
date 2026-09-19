// dto/budget/BudgetRequest.java
package com.demo.Expense_api.dto.budget;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BudgetRequest {
    @NotBlank
    private String categoryId;

    @NotNull @Positive
    private BigDecimal monthlyLimit;
}