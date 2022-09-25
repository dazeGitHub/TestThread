package com.zyz.test.threadpool.task;

/**
 * 单线程的监听任务
 */
public class ListeningTask implements Runnable{

    private int i;

    //该监听线程监听外边的请求信息
    public ListeningTask(int i){
        this.i = i;
    }

    @Override
    public void run() {
        //这里模拟一次异常
        System.out.println(Thread.currentThread().getId() + " beginning starting " + i);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        if(i == 3){
            throw new RuntimeException("异常导致程序崩溃......");
        }
        System.out.println(Thread.currentThread().getId() + " run over... " + i);
    }
}
