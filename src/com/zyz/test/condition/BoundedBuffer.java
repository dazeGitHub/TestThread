package com.zyz.test.condition;

import com.zyz.test.utils.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用这个例子理解 ReentrantLock 和 Condition 是如何使用的
 * BoundedBuffer : 缓冲区, 类似于 ArrayBlockingQueue
 */
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    //一个 lock 对象有很多 Condition 监视器, 每个监视器都是一个双向链表。链表中每个 Node 都记录了被阻塞的线程对象
    final Condition notFull = lock.newCondition();      //监测 buffer 是否满了, 如果满了, 那么不能 put(), 要等待
    final Condition notEmpty = lock.newCondition();     //监测 buffer 是否空了, 如果为空, 那么不能 take(), 要等待

    final Object[] items = new Object[20];              //buffer 的大小
    int putptr, takeptr, count;

    //往 buffer 中写数据
    public void put(Object x) throws InterruptedException {
        Log.logger.info(Thread.currentThread().getId() + " ,准备写入--" + x.toString());
        lock.lock();                                    //lock 是排它锁, 取数据 和 写入数据 不能同时进行
        try {
            while (count == items.length){
                //使用 System.out 在多线程时输出顺序有很大问题, 而使用 log4j 输出效果更好
                //System.out.println(Thread.currentThread().getId() + ", 缓存满了, 等待...");
                Log.logger.info(Thread.currentThread().getId() + ", 缓存满了, 等待...");
                notFull.await();
                //1. 使当前线程进入 waiting 状态
                //2. 释放当前线程的 lock 锁 【如果不释放锁, 别的线程是无法进入 lock() 和 unlock() 之间的代码块的】
                //3. 收到 signal 通知后, 继续从等待处向下执行程序
            }
            items[putptr] = x;
            Log.logger.info(Thread.currentThread().getId() + ", 已写入--" + x.toString());
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    //从 buffer 中读数据
    public Object take() throws InterruptedException {
        lock.lock();                                    //lock 是排它锁, 取数据 和 写入数据 不能同时进行
        try {
            while (count == 0){
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            Log.logger.info(Thread.currentThread().getId() + ", 取出了一条数据 --------" + x.toString());
            //取出数据后, 一定是不满, 那么唤醒一个等待的线程。如果是多个线程都在等待, 那么就不能用 signal() 而应该用 signalAll()
            //notFull.signal();
            notFull.signalAll();    //取出数据后唤醒所有等待的线程
            return x;
        } finally {
            lock.unlock();
        }
    }
}
