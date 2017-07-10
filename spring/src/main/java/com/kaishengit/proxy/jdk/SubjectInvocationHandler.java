package com.kaishengit.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zjs on 2017/7/10.
 */
public class SubjectInvocationHandler implements InvocationHandler {

    private Object object;
    public SubjectInvocationHandler(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("前置通知");

        Object obj = method.invoke(object,args);

        System.out.println("后置通知");

        return obj;
    }
}
