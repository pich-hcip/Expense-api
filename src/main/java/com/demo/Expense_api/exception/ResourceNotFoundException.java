// exception/ResourceNotFoundException.java
package com.demo.Expense_api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}