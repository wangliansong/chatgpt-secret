package com.omggpt.bj.exceptions;

public class JwtApiException extends  RuntimeException{
    private String code ;
    public JwtApiException(String code,String message){
        super(message);
        this.code = code;
    }
}
