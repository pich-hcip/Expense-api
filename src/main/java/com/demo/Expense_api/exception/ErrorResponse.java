// exception/ErrorResponse.java
package com.demo.Expense_api.exception;

import java.time.OffsetDateTime;

public record ErrorResponse(int status, String message, OffsetDateTime timestamp) {}