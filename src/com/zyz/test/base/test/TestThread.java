package com.zyz.test.base.test;

import com.zyz.test.base.thread.FirstThread;
import com.zyz.test.base.thread.SecondThread;

public class TestThread {

    public static void main(String[] args) {

        //希望在主线程运行的时候子线程也同时运行
        //创建线程方式一 : 继承自 Thread
        FirstThread prime = new FirstThread();
        prime.start();

        //创建线程方式二 :
        new Thread(new SecondThread()).start();

        for(int k = 0; k < 100; k++){
            System.out.println("MainThread 线程: " + Thread.currentThread().getId() + " --k = " + k);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //两个线程交替打印, 可见在很短的时间内两个线程在同时执行
        //FirstThread 线程: 16 --i = 0
        //SecondThread 线程: 17 --m = 0
        //MainThread 线程: 1 --k = 0
        //
        //SecondThread 线程: 17 --m = 1
        //MainThread 线程: 1 --k = 1
        //FirstThread 线程: 16 --i = 1
    }
}
