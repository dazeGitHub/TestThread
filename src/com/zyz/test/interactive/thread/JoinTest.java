package com.zyz.test.interactive.thread;

public class JoinTest {
    public static void main(String[] args) {
        Sleeper sleeper = new Sleeper();
        sleeper.start();

        //Thread 也实现了 Runnable 接口, 所以可以直接给 Joiner 线程对象也传一个 sleeper 线程对象
        Joiner joiner = new Joiner(sleeper);
        joiner.start();
    }
}

class Sleeper extends Thread {
    public void run() {

        System.out.println("Sleeper Thread id = " + Thread.currentThread().getId() + "   start run");
        try {
            for (int i = 0; i < 50; i++) {
                Thread.sleep(100);
                System.out.println("Sleeper Thread id = " + Thread.currentThread().getId() + " ---i = " + i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Sleeper Thread id = " + Thread.currentThread().getId() + "   end");
    }
}

//将 Sleeper 加入到 Joiner 后面
class Joiner extends Thread {
    private Sleeper m_sleeper;

    public Joiner(Sleeper sleeper) {
        m_sleeper = sleeper;
    }

    public void run() {
        System.out.println("Joiner id=" + Thread.currentThread().getId() + "  start run");

        try {
            for (int k = 0; k < 50; k++) {
                Thread.sleep(100);
                System.out.println("Joiner Thread id = " + Thread.currentThread().getId() + " ---k= " + k);
                if (k == 5) {
                    System.out.println("Sleeper 准备加入到当前线程线程 Thread id = " + Thread.currentThread().getId());
                    m_sleeper.join();    //sleeper 加入到当前线程, 当前线程被阻塞, 等待 sleeper 运行结束后这个线程才继续运行
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("Joiner Thread id=" + Thread.currentThread().getId() + "  end");
    }
}

