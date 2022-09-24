package com.zyz.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheThreadPoolTest {

    public static void main(String[] args) {
        //1. 创建线程池
        ExecutorService exec = Executors.newCachedThreadPool(); //创建了一个线程池 CachedThreadPool
        //2. 创建 20 个任务, 具体使用多少个线程去执行并不一定
        for(int i = 0; i < 20; i++){
            exec.execute(new WorkTask());
        }
        //3. 关闭线程池, 如果不关闭, 那么线程长期回收不了, 控制台的运行按钮一直是红色的待关闭状态
        exec.shutdown();
    }
}
