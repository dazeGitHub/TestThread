package com.zyz.test.interactive.thread;

/**
 * 死锁的 4个必要条件：
 *  1、互斥条件：线程使用的资源中至少有一个不能共享，如一根筷子一次只能被一人使用
 *  2、线程持有资源并等待另一个资源：拿着筷子的同时等待另一只筷子
 *  3、资源不能被抢占：不能抢别人的筷子
 *  4、循环等待资源：一直不退出
 * 防止死锁就破坏这四个条件之一即可
 *
 * 这是哲学家 (Philosopher) 吃饭的例子, 围成一桌, 筷子不是一人一双, 而是左边一支右边一支,
 * 吃饭时先取左边的筷子再取右边的筷子, 凑成一双, 再去吃饭, 吃饭的过程再去思考
 * 实际项目中模拟这个场景还是很困难的
 */
public class DieLock {
    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[30];    //人越少越容易死锁
        ChopStick left, right, first;
        left = new ChopStick();
        right = new ChopStick();
        first = left;   //先拿左手的筷子
        //因为哲学家是围成一桌, 所以一个人的右手的筷子是另一个人左手的筷子, 使用 for 循环设置筷子
        for (int i = 0; i < philosophers.length - 1; i++) {
            philosophers[i] = new Philosopher(left, right);
            left = right;                                    //吃饭时先取左边的筷子，再取右边的
            right = new ChopStick();
        }
        //最后一人的 右手 chopstick 为 first, 即 取的是 第一个人左手的 chopstick
        philosophers[philosophers.length - 1] = new Philosopher(left, first);        //会死锁
        //philosophers[i] = new Philosopher(first,left);							 //不会死锁
    }
}

class ChopStick {
    private static int counter = 0;
    private int number = counter++;

    public String toString() {
        return "ChopStick " + number;
    }
}

class Philosopher extends Thread {

    private static int counter = 0;
    private int number = counter++;
    private ChopStick leftChopstick;
    private ChopStick rightChopstick;

    public Philosopher(ChopStick left, ChopStick right) {
        leftChopstick = left;
        rightChopstick = right;
        start();
    }

    public void think() {
        try {
            sleep(13);          //33         //***思考时间越长越不容易发生死锁************
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void eat() {
        //锁定资源再请求后边的资源
        synchronized (leftChopstick) {
            System.out.println("left " + leftChopstick + "  waiting for  right " + rightChopstick);

            synchronized (rightChopstick) {
                System.out.println(this + " ------now eating ");
            }
        }
    }

    public String toString() {
        return "Philosopher, number = " + number;
    }

    public void run() {
        while (true) {
            think();
            eat();
        }
    }
}


