package com.zyz.test.threadpool.pool;

import com.zyz.test.threadpool.task.WorkTask;

public class SingleThreadPoolTest {

    public static void main(String[] args) {
        //1. 创建了一个只有唯一线程的线程池
//        ExecutorService exec = Executors.newSingleThreadExecutor();
//        //2. 创建 20 个任务, 具体使用多少个线程去执行并不一定
//        for(int i = 0; i < 20; i++){
//            exec.execute(new WorkTask());
//        }
//        //3. 关闭线程池, 如果不关闭, 那么线程长期回收不了, 控制台的运行按钮一直是红色的待关闭状态
//        exec.shutdown();

        for(int i = 0; i < 20; i++){
            new Thread(new WorkTask()).start();
        }
    }
}
