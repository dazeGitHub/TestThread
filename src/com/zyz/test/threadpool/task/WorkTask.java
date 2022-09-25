package com.zyz.test.threadpool.task;

/**
 * 任务类一般叫做 xxTask, 使用线程池运行这个 Task
 */
public class WorkTask implements Runnable{

    @Override
    public void run() {
        try {
            int random = (int)(Math.random() * 10); //产生 [0,10) 之间的随机数
            Thread.sleep(random * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getId() + " is working over");
    }
}
