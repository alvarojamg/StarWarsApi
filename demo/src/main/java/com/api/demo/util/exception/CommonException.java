package com.api.demo.util.exception;

import com.api.demo.util.enums.DefinitionMessage;

public class CommonException extends RuntimeException{

    private final DefinitionMessage definitionMessage;

    private final  String msj;


    public CommonException(DefinitionMessage definitionMessage){
        super(definitionMessage.getMessage());
        this.definitionMessage = definitionMessage;
        this.msj = definitionMessage.getMessage();
    }

    public CommonException(DefinitionMessage definitionMessage, Throwable throwable) {
        super(definitionMessage.getMessage(), throwable);
        this.definitionMessage = definitionMessage;
        this.msj = definitionMessage.getMessage();
    }




}
