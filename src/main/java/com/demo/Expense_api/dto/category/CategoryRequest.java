// dto/category/CategoryRequest.java
package com.demo.Expense_api.dto.category;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String kind;      // "EXPENSE" or "INCOME"

    @NotBlank
    private String icon;

    @NotBlank
    private String colorHex;
}