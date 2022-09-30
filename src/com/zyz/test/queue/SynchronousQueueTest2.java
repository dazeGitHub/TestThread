package com.zyz.test.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试 CachedThreadPool 和 SynchronousQueue 的关系
 */
public class SynchronousQueueTest2 {

    public static void main(String[] args) {
        /**
         * 为了执行大量短时任务, 专门设计的线程池
         * 例如 Tomcat 网站, 大量的 http 请求, 这些请求都不会执行时间很长 (是大量的短时任务)
         *
         * 有 10 个 Runnable, 都没有进入 CachedThreadPool 的 SynchronousBlockingQueue (都没有入队)
         * 当前主线程把 Runnable 任务交付过去, 但是真正的执行者是后面启动的线程
         */
        ExecutorService pool = Executors.newCachedThreadPool();
        //共 10 个任务, 看启动了多少个线程
        for(int i = 0; i < 10; ++i){
            int finalI = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getId() + " is Running, i = " + finalI);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
