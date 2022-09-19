package com.zyz.test.common.thread;

/**
 * 测试线程优先级
 */
public class PriorityThread {

    public static void main(String[] args) {
        //线程优先级高的先结束
        for(int i = 0; i < 10; i++){
            new Thread(new SimplePriority(Thread.MAX_PRIORITY)).start();
            new Thread(new SimplePriority(Thread.MIN_PRIORITY)).start();
        }
    }
}

class SimplePriority implements Runnable{

    private int priority;
    private volatile double d;

    public SimplePriority(int priority){
        this.priority = priority;
    }

    //如果是一般的任务, 那么线程的优先级很难体现出来 [优先级高的能获得更多的 CPU 资源], 所以可以做一个大的浮点运算
    //在浮点运算里面, 因为值非常大, 耗费的时间很长, 这是在某个线程里加入 yield, 产生暂时的停顿, 这样就可以产生抢资源的效果
    @Override
    public void run() {
        //当前线程在运行之前先设置优先级
        Thread.currentThread().setPriority(this.priority);
        System.out.println("priority = " + Thread.currentThread().getPriority()
                + "---- thread Id = " + Thread.currentThread().getId()
                + " is starting"
        );
        //做一个大型的浮点运算, cpu 或充分的运行起来, 运行的时间比较长, 这样才能看出效果, 否则优先级的情况很难模拟
        for(int i = 0; i < 100_000; i++){
            d += (Math.PI + Math.E) / (double) i;
            if(i % 100 == 0){
                Thread.yield(); //CPU 暂停当前线程的运行
            }
        }
        System.out.println("priority = " + Thread.currentThread().getPriority()
                + "---- thread Id = " + Thread.currentThread().getId()
                + " is over"
        );
    }
}