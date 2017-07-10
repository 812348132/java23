package com.kaishengit.proxy.jdk;

import com.kaishengit.proxy.jdk.Computer;
import com.kaishengit.proxy.jdk.Dell;
import com.kaishengit.proxy.jdk.SubjectInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * Created by zjs on 2017/7/10.
 */
public class Test {


    public static void main(String[] args) {

        Dell dell = new Dell();

        SubjectInvocationHandler subjectInvocationHandler = new SubjectInvocationHandler(dell);

        Computer computer = (Computer) Proxy.newProxyInstance(dell.getClass().getClassLoader(),dell.getClass().getInterfaces(),subjectInvocationHandler);

        computer.sale();
    }
}
