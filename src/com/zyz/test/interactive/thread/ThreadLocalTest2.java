package com.zyz.test.interactive.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

//ThreadLocalTest 类比较绕, 所以使用 ThreadLocalTest2 重写一下
class ThreadLocalTest2 {

    //AtomicInteger 可以在一个高并发的环境下, 使 int 值可以进行原子性的变化
    //AtomicInteger 可以保证变量的原子性和同步性, 常用方法是 getAndIncrement() 加一 和 getAndDecrement() 减一
    //AtomicInteger 采用的是 CAS 的算法, CAS 是无锁但是在高并发的环境下保证安全的一种算法
    private static final AtomicInteger nextId = new AtomicInteger(0);

    //静态代码块给静态变量赋值, 通常只有一次操作, 即只会调用一次 ThreadLocal 的构造函数
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static int get(){
        return threadLocal.get();
    }

    public static void set(Integer value){
        threadLocal.set(value);
    }

    public static void main(String[] args) {

        //使用线程池模拟一个多线程环境
        ExecutorService pool = Executors.newFixedThreadPool(8); //Executors.newCachedThreadPool()

        //使用线程池来执行异步任务, 有可能启用 10 个线程, 线程也可能被重用,
        //如果是 10 个线程, 那么每个线程都会调用 ThreadId2 的 get() 方法
        for(int i = 0; i < 10; i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    ThreadLocalTest2.set(nextId.getAndIncrement());
                    int threadLocalId = ThreadLocalTest2.get();
                    System.out.println("curThreadId = " + Thread.currentThread().getId() + " -- threadLocalId = " + threadLocalId);
                }
            });
        }

        pool.shutdown();
//      线程 curThreadId 一样的, threadLocal 的 set() 方法回把之前的 value 值覆盖掉,
//      可以使用 ThreadLocal 来存储数据库连接的 connection 对象非常合适
//      curThreadId = 23 -- threadLocalId = 7
//      curThreadId = 23 -- threadLocalId = 8
//      curThreadId = 23 -- threadLocalId = 9
//      curThreadId = 17 -- threadLocalId = 1
//      curThreadId = 19 -- threadLocalId = 3
//      curThreadId = 16 -- threadLocalId = 0
//      curThreadId = 22 -- threadLocalId = 6
//      curThreadId = 21 -- threadLocalId = 5
//      curThreadId = 18 -- threadLocalId = 2
//      curThreadId = 20 -- threadLocalId = 4
    }
}