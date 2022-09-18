package com.zyz.test.thread.bean;

public class FirstThread extends Thread{

    /**
     * 很重要 : 这里没有异常抛出, 因为有了异常也无法抛到主线程
     */
    @Override
    public void run() {
        for(int i = 0; i < 50; i++){
            //找到这是哪个线程, 可以打印线程 id
            System.out.println("FirstThread 线程: " + Thread.currentThread().getId() + " --i = " + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
