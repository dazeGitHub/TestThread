package com.zyz.test.threadpool.endtask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolShutdownTest {

    public static void main(String[] args) {
        //ExecutorService pool = Executors.newFixedThreadPool(3);
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++){
                        System.out.println(Thread.currentThread().getId() + "--- is running");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            });
            try {
                Thread.sleep(2000); //每隔2秒提交一个任务
            } catch (InterruptedException e) {
            }
        }
        //多次 shutdown() 也没有影响
        pool.shutdownNow();
        pool.shutdownNow();
    }
}
