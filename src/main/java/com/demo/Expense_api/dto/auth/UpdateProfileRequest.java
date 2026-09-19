// dto/user/UpdateProfileRequest.java
package com.demo.Expense_api.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @NotBlank
    private String fullName;

    @NotBlank @Email
    private String email;

    private String phoneNumber;
}