package com.zyz.test.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用这个例子理解 ReentrantLock 和 Condition 是如何使用的
 * BoundedBuffer : 缓冲区, 类似于 ArrayBlockingQueue
 */
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();      //监测 buffer 是否满了, 如果满了, 那么不能 put(), 要等待
    final Condition notEmpty = lock.newCondition();     //监测 buffer 是否空了, 如果为空, 那么不能 take(), 要等待

    final Object[] items = new Object[20];              //buffer 的大小
    int putptr, takeptr, count;

    //往 buffer 中写数据
    public void put(Object x) throws InterruptedException {
        lock.lock();                                    //lock 是排它锁, 取数据 和 写入数据 不能同时进行
        try {
            while (count == items.length){
                //使用 System.out 在多线程时输出顺序有很大问题, 而使用 log4j 输出效果更好
                //System.out.println(Thread.currentThread().getId() + ", 缓存满了, 等待...");

                notFull.await();
            }
            items[putptr] = x;
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
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
