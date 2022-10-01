package com.zyz.test.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor 使用 SynchronousQueue :
 *      1. corePoolSize 线程启动: 根据任务数量, 直接启动线程, 这些线程是不进阻塞队列的。
 *                         即使空闲也不会回收。
 *      2. 入队的任务数量 : 扣除 corePoolSize 的线程数, 其他任务先进入 LinkedBlockingQueue 队列
 *
 *      3. 最大线程数是如何增加 : 未入队列的任务, 会通过 maxPoolSize 来增加线程, 去执行任务
 *                            最大线程数是包含核心线程数的, 是所有线程数的最大数量
 *                            如果 maxPoolSize 不能完全解决队列外面的任务, 会抛出异常
 *                            注意 : 已经入队的任务是不会用 maxPoolSize 来执行的
 *
 */
public class ThreadPoolExecutorTest2 {

    public static void main(String[] args) {
        //1. 测试任务全入队后, 是否会增加线程 ?
        //使用 SynchronousQueue, 10 个任务, 1 个核心线程, 那么 9 个任务全部在队列外
        //如果最大线程数为 5, 那么就会抛异常
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(
//                1, 5,
//                10, TimeUnit.SECONDS,
//                new SynchronousQueue<>()
//        );
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1, 5,
                20, TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
        //打印线程池信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("core:" + pool.getCorePoolSize());   //核心线程数
                    System.out.println("max:" + pool.getMaximumPoolSize()); //最大线程数
                    System.out.println("active:" + pool.getActiveCount());  //激活的数量
                    System.out.println("task end --" + pool.getCompletedTaskCount()); //已经完成的任务数
                    System.out.println("pool size:" + pool.getPoolSize());  //当前所有线程数
                    System.out.println("queue size:" + pool.getQueue().size()); //队列的大小
                    System.out.println("------------------------------------");

                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //添加 10 个任务
        for(int i = 0; i < 10; i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getId() + " running ......");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        pool.shutdown();
    }
}
