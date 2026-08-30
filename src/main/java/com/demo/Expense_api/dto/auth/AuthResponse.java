// AuthResponse.java
package com.demo.Expense_api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UUID userId;
    private String fullName;
    private String email;
}