package com.zyz.test.locksupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 的 Condition 的 await() 和 signal() 的方式通知阻塞的线程
 */
public class TestRWait {

    public static void main(String[] args) {

        ReentrantLock rLock = new ReentrantLock();
        Condition condition = rLock.newCondition();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i = 0; i < 100; i++){
                    sum += i;
                }
                try {
                    //在调用 condition 的 await() 方法前, 一定要锁住, 否则会报 java.lang.IllegalMonitorStateException
                    rLock.lock();             //必须要提前锁定
                    condition.await();        //阻塞当前线程
                    rLock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sum = " + sum); //sum = 4950
            }
        });
        thread.start();

        try {
            Thread.sleep(5000);         //主线程阻塞5秒
            rLock.lock();
            condition.signal();               //通知监视器解锁
            rLock.unlock();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
