package com.zyz.test.syn_renlock.syn_invalid;

/**
 * 测试使用对象引用直接调用 Runnable 对象的 run() 方法时, synchronized 关键字不好使
 */
public class SynTestRunnable implements Runnable {

    int b = 100;

    @Override
    public void run() {
        try {
            m1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public void m1() throws InterruptedException {
        b = 1000;
        Thread.sleep(5000);
        System.out.println("m1   b=" + b);
    }

    synchronized public void m2() throws InterruptedException {
        Thread.sleep(2000);
        b = 2000;
        System.out.println("m2   b = " + b);
    }

    public static void main(String[] args) throws InterruptedException {
        //启动了一个线程, 执行 runnable 对象的 run 方法, 再调用 runnable 对象的 m1() 方法
        SynTestRunnable runnable = new SynTestRunnable();
        Thread thread = new Thread(runnable);
        thread.start();

        //在当前线程, 直接调用 runnable 对象的 m2() 方法, 这个时候 synchronized 还是否有用 ?
        runnable.m2(); //答案 : 当前调用模式, runnable.m2() 执行时, m2() 前的 synchronized 没有起作用
        System.out.println("main end, b = " + runnable.b);
    }
}
