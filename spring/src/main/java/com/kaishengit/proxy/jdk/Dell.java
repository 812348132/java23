package com.kaishengit.proxy.jdk;

import com.kaishengit.proxy.jdk.Computer;

/**
 * Created by zjs on 2017/7/10.
 */
public class Dell implements Computer {
    @Override
    public void sale() {
        System.out.println("Dell sale");
    }
}
