package com.zyz.test.syn_renlock.syn4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用 synchronized 修饰 m1() 和 m2(), 一个线程进入 m1() 时, 另一个线程能否进入 m2()里 ?
 *    1. 如果 synchronized 生效 : 一个线程进入 m1() 时, 那么另一个线程将会被阻塞, 无法进入 m2(), 两个方法是互斥的
 *    2. 如果 synchronized 不生效 : 一个线程进入 m1() 时, 另一个线程也可以进入 m2(), 两个方法是并发进行的
 * 实际是情况 1, synchronized 是生效的
 *
 * 静态方法加 synchronized 关键字的意义 :
 *    例如文件写入的静态方法, 并发时需要保证文件写入顺序, 就需要 synchronized 关键字
 *
 * 总结 : 静态方法使用 synchronized 时, 当一个线程进入这个方法如 m1() 后, 本质是给常量池里的这个 Class 加锁
 *    其他线程想进入 m1() 或者 m2(), 都需要先获得这个 Class 锁。获得锁之后才能进入, 否则就排队等待。
 *    因此 m1() 和 m2() 之间是互斥的。
 */
public class SynStaticTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        //10 个线程有的执行 m1 方法, 有的执行 m2 方法
        for (int i = 0; i < 10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    int rand = (int)(Math.random() * 10);
                    if(rand % 2 == 0){
                        SynStaticTool.m1();
                    }else{
                        SynStaticTool.m2();
                    }
                }
            });
        }
        pool.shutdown();
    }
}
