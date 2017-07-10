package com.kaishengit.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by zjs on 2017/7/10.
 */
public class AopAspect {

    public void beforeAdvice(){
        System.out.println("before...");
    }

    public void afterReturning(){
        System.out.println("afterReturning...");
    }

    public void afterThrowing(Exception e){
        System.out.println(e.getMessage());
    }

    public void after(){
        System.out.println("after......");
    }

    public void around(ProceedingJoinPoint proceedingJoinPoint){
        try {
            /*前置通知*/
            System.out.println("前置通知");
            proceedingJoinPoint.proceed();
            System.out.println("后置通知");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
           /*最终通知*/
            System.out.println("最终通知");
        }

    }
}
