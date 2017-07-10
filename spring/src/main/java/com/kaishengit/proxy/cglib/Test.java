package com.kaishengit.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by zjs on 2017/7/10.
 */
public class Test {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Moves.class);
        enhancer.setCallback(new SubjectMethodInterceptor());

        Moves moves = (Moves) enhancer.create();
        moves.move();


      /*  Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Moves.class);
        enhancer.setCallback(new SubjectMethodInterceptor());

        Moves moves = (Moves) enhancer.create();

        moves.move();*/
    }
}
