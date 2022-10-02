package com.zyz.test.threadpool.endtask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用 future.get() 获取 Callable 的 call() 方法的返回值
 */
public class FutureCancelTaskTest1 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 0; i < 20; i++){
            //先 submit 一个 Task 然后调用 future.get() 得到值, 而不是一次就 submit 很多 Task
            Future<String> future = pool.submit(new WorkTask3(i));
            try{
                String value = future.get();
                System.out.println("future.get() = [" + value +"]");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }
}

class WorkTask3 implements Callable<String>{
    private int taskId; //任务号

    public WorkTask3(int taskId){
        this.taskId = taskId;
    }

    @Override
    public String call(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread id = " + Thread.currentThread().getId() + ", taskId is " + taskId;
    }
}