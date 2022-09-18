package com.zyz.test.common.thread;

public class YieldThread implements Runnable{
    private Integer x = 0;

    @Override
    public void run() {
        for(int i = 0; i < 50; i++){
            //Thread.currentThread().yield(); //注册此句, 其他线程可能无法抢入
            System.out.println("线程 ID" + Thread.currentThread().getId() + " x = " + x);
            x++;
        }
    }

    public static void main(String[] args) {
        YieldThread yieldThread = new YieldThread();
        new Thread(yieldThread).start();
        new Thread(yieldThread).start();
    }
}
