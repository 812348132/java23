package com.kaishengit.exception;

/**
 * Created by zjs on 2017/7/19.
 */
public class AuthenticationException extends ServiceException {

    public AuthenticationException(){}
    public AuthenticationException(String message){
        super(message);
    }

    public AuthenticationException(String message,Throwable throwable){
        super(message,throwable);
    }
}
