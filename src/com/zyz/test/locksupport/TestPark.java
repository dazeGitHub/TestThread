package com.zyz.test.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用 LockSupport 的 park() 方式实现线程阻塞
 */
public class TestPark {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i = 0; i < 100; i++){
                    sum += i;
                }
                LockSupport.park();   //阻塞当前线程的执行(除非线程已经有许可证才不阻塞)
                System.out.println("sum = " + sum); //sum = 4950
            }
        });
        thread.start();

        try {
            //Thread.sleep(5000);         //主线程阻塞5秒
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LockSupport.unpark(thread);     //给该线程发放许可证, 如果该线程在 park() 处被阻塞, 那么它将被解除阻塞
    }
}
