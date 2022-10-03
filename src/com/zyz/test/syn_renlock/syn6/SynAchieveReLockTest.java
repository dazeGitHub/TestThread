package com.zyz.test.syn_renlock.syn6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用 Synchronized 实现重入锁
 */
public class SynAchieveReLockTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        LogWidget log = new LogWidget();

        for(int i = 0; i < 10; ++i){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    log.doSomething();
                }
            });
        }
        pool.shutdown();
    }
}
