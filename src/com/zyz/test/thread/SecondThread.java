package com.zyz.test.thread;

public class SecondThread implements Runnable{

    /**
     * 很重要 : 这里没有异常抛出, 因为有了异常也无法抛到主线程
     */
    @Override
    public void run() {
        for(int m = 0; m < 50; m++){
            //找到这是哪个线程, 可以打印线程 id
            System.out.println("SecondThread 线程: " + Thread.currentThread().getId() + " --m = " + m);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
