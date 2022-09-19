package com.zyz.test.interactive.thread;

import java.util.concurrent.Semaphore;

/**
 * 信号量测试
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore smp = new Semaphore(5);    //5个许可证，表示5台实验机
        //模拟20个学生
        for (int i = 0; i < 20; i++) {
            StudentTask studentTask = new StudentTask(smp, i);
            new Thread(studentTask).start();
        }
    }
}

/**
 * 学生任务
 */
class StudentTask implements Runnable {

    private Semaphore smp;
    private int num;

    public StudentTask(Semaphore smp, int num) {
        this.smp = smp;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            smp.acquire();       //排队等待获取许可证
            //开始做实验
            Thread.sleep(2000);
            System.out.println("Thread id = " + Thread.currentThread().getId() + " ------ num = " + num + " ,实验完毕");
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            smp.release();      //释放许可证     //在 finally 里释放许可证是为了防止出现异常导致释放不掉
        }
    }
}
