// dto/user/UpdateAvatarRequest.java
package com.demo.Expense_api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAvatarRequest {
    @NotBlank
    private String avatarUrl;
}