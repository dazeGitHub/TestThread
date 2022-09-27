package com.zyz.test.interactive.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestCallable {
    public static void main(String[] args) {

        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return (int)(Thread.currentThread().getId());
            }
        });

        new Thread(task , "有返回值的线程").start();
        try {
            System.out.println("子线程的返回值：" +  task.get()); //子线程的返回值：16
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
