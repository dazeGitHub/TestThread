package com.zyz.test.syn_renlock.syn6;

public class Widget {

    synchronized public void doSomething() {
        try {
            Thread.sleep(200);
        }catch (Exception e){

        }
        System.out.println("widget is do something..., thread id = " + Thread.currentThread().getId());
    }
}
