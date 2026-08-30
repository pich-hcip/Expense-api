// RegisterRequest.java
package com.demo.Expense_api.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank @Email(message = "Valid email is required")
    private String email;

    private String phoneNumber;

    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}