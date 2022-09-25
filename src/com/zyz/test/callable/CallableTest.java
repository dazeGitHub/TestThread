package com.zyz.test.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0; i < 20; i++){
            //每个线程执行任务后就会返回 Future, Future 的返回值取决于 Callable 任务
            Future<String> future = executorService.submit(new TaskResult(i));
            try{
                String value = future.get();
                System.out.println("MainThread id = " + Thread.currentThread().getId() + ", value = [ " + value + " ]");      //输出线程的运行结果
            }catch (Exception e){
            }
        }
        executorService.shutdown();
    }
}

class TaskResult implements Callable<String> {

    private int i; //任务号

    //将执行任务的任务号传递过来
    public TaskResult(int i){
        this.i = i;
    }

    @Override
    public String call() throws Exception {
        try {
            Thread.sleep(200);
        }catch (Exception e){
        }
        return "ChildThread id = " + Thread.currentThread().getId() + " result is " + i;
    }
}