package com.zyz.test.syn_renlock.syn6;

public class LogWidget extends Widget {

    synchronized public void doSomething() {
        //super.doSomething() 也加了 synchronized, 如果 synchronized 不是可重用的,
        //那么这里调用 super.doSomething() 就会再次等待获得锁, 但是当前已经获得了锁, 最后导致死锁
        try {
            Thread.sleep(200);
        } catch (Exception e) {

        }
        System.out.println("son is do something..., thread id = " + Thread.currentThread().getId());
        super.doSomething();
        doOthers();
    }

    synchronized public void doOthers() {
        System.out.println("son is do Others..., thread id = " + Thread.currentThread().getId());
    }
}
