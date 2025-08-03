package com.api.demo.util.exception;

import com.api.demo.util.enums.DefinitionMessage;

import javax.management.loading.MLetContent;


public class CommonException extends RuntimeException {
    private final DefinitionMessage definitionMessage;

    public CommonException(DefinitionMessage definitionMessage) {
        super(definitionMessage.getMessage());
        this.definitionMessage = definitionMessage;
    }

    public CommonException(DefinitionMessage definitionMessage, Throwable throwable) {
        super(definitionMessage.getMessage(), throwable);
        this.definitionMessage = definitionMessage;
    }

    public DefinitionMessage getDefinitionMessage() {
        return definitionMessage;
    }
}
