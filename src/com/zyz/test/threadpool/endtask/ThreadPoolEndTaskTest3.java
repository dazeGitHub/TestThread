package com.zyz.test.threadpool.endtask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用 object.wait() 和 object.notify() 实现 线程池任务的暂停和恢复
 */
public class ThreadPoolEndTaskTest3 {

    public static void main(String[] args) {
        //ExecutorService pool = Executors.newFixedThreadPool(3);
        ExecutorService pool = Executors.newCachedThreadPool();

        //为了调用 task 的 stopTask() 方法, 使用一个 List 集合来保存这些 Task
        List<WorkTask2> allTasks = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            WorkTask2 task = new WorkTask2();
            allTasks.add(task);
            pool.execute(task);
            try {
                Thread.sleep(500); //每隔2秒提交一个任务
            } catch (InterruptedException e) {
            }
        }

        try {
            Thread.sleep(3000);     //5 秒后关闭任务
            System.out.println("发出暂停执行线程任务的指令...");
            for(WorkTask2 task: allTasks){
                task.waitTask();          //让每一个执行任务的线程, 进入 waiting blocking 状态
            }
            //通知休眠的线程恢复工作
            System.out.println("开始间隔 8 秒");
            Thread.sleep(8000);
            System.out.println("发出恢复执行线程任务的指令...");
            for(WorkTask2 task: allTasks){
                task.notifyTask();        //让每一个执行任务的线程, 从 waiting blocking 状态中恢复运行
            }
        } catch (InterruptedException e) {
        }

        //多次 shutdown() 也没有影响
        pool.shutdownNow();
    }
}

class WorkTask2 implements Runnable{
    boolean bRun = true;
    boolean bWait = false;

    @Override
    public void run() {
        for(int i = 0; i < 1000; i++){
            if(bRun){ //变量 bRun 是从内部类访问的, 必须是 final
                if(bWait){
                    try {
                        System.out.println(Thread.currentThread().getId() + " start waiting...");
                        synchronized (this){
                            //wait() 方法的调用, 必须要在 synchronized 代码块中, 否则没有 wait blocking 效果
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                    }
                }
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

    public void waitTask(){
        bWait = true;
    }

    public void notifyTask(){
        bWait = false;
        //notify() 方法的调用, 必须要在 synchronized 代码块中, 否则没有效果
        synchronized (this){
            this.notify();
        }
    }
}