package com.zyz.test.threadpool.endtask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 结束线程池任务
 * 方案二 : 匿名内部类 Runnable + Runnable 类全局变量做为开关
 */
public class ThreadPoolEndTaskTest2 {

    public static void main(String[] args) {
        //ExecutorService pool = Executors.newFixedThreadPool(3);
        ExecutorService pool = Executors.newCachedThreadPool();

        //为了调用 task 的 stopTask() 方法, 使用一个 List 集合来保存这些 Task
        List<WorkTask> allTasks = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            WorkTask task = new WorkTask();
            allTasks.add(task);
            pool.execute(task);
            try {
                Thread.sleep(2000); //每隔2秒提交一个任务
            } catch (InterruptedException e) {
            }
        }

        try {
            Thread.sleep(5000);     //5 秒后关闭任务
            System.out.println("发出关闭线程任务的指令...");
            for(WorkTask task: allTasks){
                task.stopTask();          //分别停止每一个线程任务
            }
        } catch (InterruptedException e) {
        }

        //多次 shutdown() 也没有影响
        pool.shutdownNow();
    }
}

class WorkTask implements Runnable{
    boolean bRun = true;

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

    public void stopTask(){
        bRun = false;
    }
}