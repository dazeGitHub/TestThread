package com.zyz.test.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProductConsumerTest {

    public static void main(String[] args) {
        BlockingQueue q = new LinkedBlockingQueue(); //所有的 生产者 和 消费者 使用同一个 BlockingQueue
        Producer p = new Producer(q);   //一个生产者
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);  //多个消费者
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();         //每个生产者和消费者, 都是独立的线程
    }
}

class Producer implements Runnable {
    private final BlockingQueue queue;

    Producer(BlockingQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        try {
//          while (true) {
//              queue.put(produce());
//          }
            for(int i = 0; i < 100; i++){
                queue.put(new Integer(i));
                System.out.println(Thread.currentThread().getId() + "-- 插入队列 --" + i);
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            //... handle ...
        }
    }
}

class Consumer implements Runnable {
    private final BlockingQueue queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object object = queue.take();
                System.out.println(Thread.currentThread().getId() + " ------------ 取出 --- " + object.toString());
            }
        } catch (InterruptedException ex) {
            //... handle ...
        }
    }
}