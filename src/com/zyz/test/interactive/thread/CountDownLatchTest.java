package com.zyz.test.interactive.thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private static final int SIZE = 10;

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(SIZE); //计数器数量是 10

        //汽车总装任务
        WaitingTask waitingTask = new WaitingTask(cdl);
        new Thread(waitingTask).start();  //先等待其他任务完成

        //开启任务, 任务数和计数器应当一样
        for(int i = 0; i < SIZE; i++){
            WorkingTask workingTask = new WorkingTask(cdl);
            new Thread(workingTask).start();
        }
    }
}

/**
 * 模拟汽车零部件生产
 */
class WorkingTask implements Runnable{
    private CountDownLatch cdl;

    public WorkingTask(CountDownLatch cdl){
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try{
            //do working
            int rand = (int) (Math.random() * 10); //返回 0 到 1 之间的数
            Thread.sleep(rand * 1000L); //随机等待几秒
            System.out.println("Thread id = " + Thread.currentThread().getId() + " 任务完成");
            cdl.countDown(); //工作完了将计数器减 1
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

/**
 * 模拟汽车总装业务 (它是最后的步骤)
 */
class WaitingTask implements Runnable{
    private CountDownLatch cdl;

    //通过计数器等待
    public WaitingTask(CountDownLatch cdl){
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread id = " + Thread.currentThread().getId() + " 正在等待其他任务完成...");
            cdl.await();
            Thread.sleep(1000);
            System.out.println("Thread id = " + Thread.currentThread().getId() + " 汽车总装任务完成...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
