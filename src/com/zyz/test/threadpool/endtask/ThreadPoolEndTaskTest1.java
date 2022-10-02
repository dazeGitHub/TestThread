package com.zyz.test.threadpool.endtask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 结束线程池任务
 * 方案一 : 匿名内部类 Runnable + 局部变量做为开关 (无法使用)
 */
public class ThreadPoolEndTaskTest1 {

    public static void main(String[] args) {
        //ExecutorService pool = Executors.newFixedThreadPool(3);
        ExecutorService pool = Executors.newCachedThreadPool();
        final boolean bRun = true;

        for(int i = 0; i < 10; i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++){
                        if(bRun){ //变量 bRun 是从内部类访问的, 必须是 final
                            System.out.println(Thread.currentThread().getId() + "--- is running");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                //e.printStackTrace();
                            }
                        }
                    }
                }
            });
            try {
                Thread.sleep(2000); //每隔2秒提交一个任务
            } catch (InterruptedException e) {
            }
        }

        try {
            Thread.sleep(5000);
            //bRun = false; //5 秒后关闭任务
        } catch (InterruptedException e) {

        }

        //多次 shutdown() 也没有影响
        pool.shutdownNow();
    }
}
