package com.zyz.test.threadpool;

import com.zyz.test.threadpool.task.ListeningTask;
import com.zyz.test.threadpool.task.WorkTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试单线程的应用场景 : 监听任务
 */
public class SingleThreadPoolTest2 {
    public static void main(String[] args) {

        ExecutorService exec = Executors.newSingleThreadExecutor();
        //2. 创建 20 个任务, 具体使用多少个线程去执行并不一定
        for(int i = 0; i < 20; i++){
            exec.execute(new ListeningTask(i));
        }
        //3. 关闭线程池, 如果不关闭, 那么线程长期回收不了, 控制台的运行按钮一直是红色的待关闭状态
        exec.shutdown();
    }
}
