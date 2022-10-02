package com.zyz.test.threadpool.endtask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCancelTaskTest2 {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(2);
        List<Future> futureList = new ArrayList<>();

        for(int i = 0; i < 50; i++){
            //先 submit 一个 Task 然后调用 future.get() 得到值, 而不是一次就 submit 很多 Task
            Future<String> future = pool.submit(new WorkTask4(i));
            futureList.add(future);

            //因为 future.get() 会阻塞当前线程, 直到 future.get() 返回值为止,
            //导致最后所有任务都执行完了有了结果了才取消任务, 所有这里将 future.get() 注释掉
//            try{
//                String value = future.get();
//                System.out.println("future.get() = [" + value +"]");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }

        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        //在 future 的任务进行过程中, 取消任务
        System.out.println("取消线程任务, start...");
        for(Future future: futureList){
            future.cancel(true);    //取消正在执行的任务
        }
        pool.shutdown();
    }
}

class WorkTask4 implements Callable<String>{
    private int taskId; //任务号

    public WorkTask4(int taskId){
        this.taskId = taskId;
    }

    @Override
    public String call(){
        System.out.println("Thread id = " + Thread.currentThread().getId() + ", taskId is " + taskId);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        return "Thread id = " + Thread.currentThread().getId() + ", taskId is " + taskId;
    }
}