package com.kaishengit.exception;

/**
 * Created by zjs on 2017/7/19.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(){}
    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message,Throwable throwable){
        super(message,throwable);
    }
}
