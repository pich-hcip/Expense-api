// dto/category/CategoryResponse.java
package com.demo.Expense_api.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponse {
    private UUID id;
    private String name;
    private String kind;      // "EXPENSE" or "INCOME"
    private String icon;
    private String colorHex;
    private long transactionCount;
}