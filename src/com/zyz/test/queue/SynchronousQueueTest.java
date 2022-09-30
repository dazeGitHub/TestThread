package com.zyz.test.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * BlockingQueue 适合生产者消费者模式, 而 SynchronousQueue 实现了 BlockingQueue,
 * 所以 SynchronousQueue 也适合生产者消费者模式
 */
public class SynchronousQueueTest {

    public static void main(String[] args) {
        //BlockingQueue q = new LinkedBlockingQueue();
        BlockingQueue q = new SynchronousQueue(); //所有的 生产者 和 消费者 使用同一个 BlockingQueue
        Producer2 p = new Producer2(q);   //一个生产者
        Consumer2 c1 = new Consumer2(q);
        Consumer2 c2 = new Consumer2(q);  //多个消费者
        new Thread(p).start();
//        new Thread(c1).start();
//        new Thread(c2).start();         //每个生产者和消费者, 都是独立的线程
    }
}

class Producer2 implements Runnable {
    private final BlockingQueue queue;

    Producer2(BlockingQueue q) {
        queue = q;
    }

    @Override
    public void run() {
        try {
//          while (true) {
//              queue.put(produce());
//          }
            for(int i = 0; i < 100; i++){
                System.out.println("准备插入 -----" + i);
                //如果只有 put() 操作, 没有数据取出, 则线程阻塞在当前位置
                //LinkedBlockingQueue 中写入数据, 不受 take() 的影响
                //SynchronousQueue 操作, 如果只有 put() 操作, 没有配对的 take(), 则线程阻塞在当前位置
                queue.put(new Integer(i));
                System.out.println(Thread.currentThread().getId() + "-- 插入队列 --" + i);
                //Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            //... handle ...
        }
    }
}

class Consumer2 implements Runnable {
    private final BlockingQueue queue;

    Consumer2(BlockingQueue q) {
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