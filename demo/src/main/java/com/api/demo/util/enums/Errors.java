package com.api.demo.util.enums;


public enum  Errors implements DefinitionMessage {
    ERROR_INTERNAL("001", "Internal server error");


    private String key;
    private String message;

    Errors(String key, String message) {
        this.key = key;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }
}
