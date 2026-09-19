// dto/user/UserProfileResponse.java
package com.demo.Expense_api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserProfileResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String avatarUrl;
    private String defaultCurrency;
}