package com.zyz.test.syn_renlock.syn_invalid;

public class SynTestRunnable2 implements Runnable {

    int b = 100;

    @Override
    public void run() {
        try {
            int rand = (int)(Math.random() * 10);
            if(rand % 2 == 0){
                m1();
            }else{
                m2();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public void m1() throws InterruptedException {
        b = 1000;
        Thread.sleep(5000);
        System.out.println("m1   b = " + b);
    }

    synchronized public void m2() throws InterruptedException {
        Thread.sleep(5000);
        b = 2000;
        System.out.println("m2   b = " + b);
    }

    public static void main(String[] args) throws InterruptedException {
        //启动了一个线程, 执行 runnable 对象的 run 方法, 再调用 runnable 对象的 m1() 方法
        SynTestRunnable2 runnable = new SynTestRunnable2();

        //两个线程公用一个 Runnable 对象, m1() 和 m2() 的 Synchronized 都是起作用的
        Thread thread = new Thread(runnable);
        thread.start();

        //m1() 与 m2() 在两个子线程中运行, 一个方法获得了 runnable 对象的锁后, 另一个方法必须排队等待
        new Thread(runnable).start();

        System.out.println("main end, b = " + runnable.b);
    }
}
