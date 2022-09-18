package com.zyz.test.common.thread;

/**
 * 1. 守护线程就是运行在程序后台的线程
 * 2. 如果虚拟机中只有 Daemon thread 在运行, 其他级别高的线程都运行完了, 则守护线程也会执行完, 虚拟机退出
 */
public class DamonThread {
    public static void main(String[] args) {

        Thread thread = new Thread(
            new Runnable() {    //定义了一个实现了 Runnable 接口的匿名类
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(500);
                            System.out.println("--------------- in damon thread");
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        );
        //修改参数 : true 为 Daemon thread, false 为 user thread
        thread.setDaemon(true);
        thread.start();

        //如果虚拟机中只有 Damon thread 运行, 则虚拟机退出
        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(500);
                System.out.println("main thread i = " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("************* main thread end ************");
    }
}
