package com.zyz.test.syn_renlock.syn7;

import com.zyz.test.syn_renlock.syn4.SynStaticTool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynStaticTest2 {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        //10 个线程有的执行 m1 方法, 有的执行 m2 方法
        //m1() 和 m2() 给静态方法加锁, m3() 和 m4() 给对象加锁
        SynStaticTool2 tool = new SynStaticTool2();
        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    int rand = (int)(Math.random() * 10);
                    if(rand % 2 == 0){
                        SynStaticTool2.m1();
                        //tool.m3();
                        tool.m5();
                    }else{
                        SynStaticTool2.m2();
                        //tool.m4();
                        tool.m6();
                    }
                }
            });
        }
        pool.shutdown();
    }
}
