package com.api.demo.util.enums;


public enum Errors implements DefinitionMessage {
    ERROR_INTERNAL("001", "Internal server error"),
    ERROR_GENDER("002", "Invalid gender. Allowed values: male, female, n/a, none, unknown"),
    ERROR_MAX_CHARACTERS("003", "Exceeds max characters");

    private final String key;
    private final String message;

    Errors(String key, String message) {
        this.key = key;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return key;
    }
}