package com.zyz.test.locksupport;

/**
 * 使用 wait() 的方式通知阻塞的线程
 * wait() 和 notify() 必须成对出现, 而且 wait() 方法要先执行
 */
public class TestWait {

    public static void main(String[] args) {
        Object waitObject = new Object();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i = 0; i < 100; i++){
                    sum += i;
                }
                try {
                    //不加 synchronized (waitObject) 会报 java.lang.IllegalMonitorStateException 异常
                    synchronized (waitObject){
                        waitObject.wait();  //阻塞当前线程的执行
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sum = " + sum); //sum = 4950
            }
        });
        thread.start();

        try {
            Thread.sleep(5000);         //主线程阻塞5秒
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        synchronized (waitObject){
            waitObject.notify();    //主线程发出通知, 解除子线程的阻塞
        }
    }
}
