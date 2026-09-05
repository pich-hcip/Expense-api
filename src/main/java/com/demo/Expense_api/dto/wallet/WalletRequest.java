// dto/wallet/WalletRequest.java
package com.demo.Expense_api.dto.wallet;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String currencyCode;

    @NotNull
    private BigDecimal balance;

    private String icon;
    private String colorHex;
    private boolean isDefault;
}