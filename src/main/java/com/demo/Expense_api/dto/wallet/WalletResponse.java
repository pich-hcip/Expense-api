// dto/wallet/WalletResponse.java
package com.demo.Expense_api.dto.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class WalletResponse {
    private UUID id;
    private String name;
    private String currencyCode;
    private BigDecimal balance;
    private boolean isDefault;
}