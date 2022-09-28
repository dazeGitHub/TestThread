package com.zyz.test.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 是重入锁, 调用 lock() 时 :
 * 1. 如果锁没有被另一个线程占用并且立即返回, 则将锁定计数设置为 1
 * 2. 如果当前线程已经保持锁定, 又调用了一次 lock() 方法, 那么保持计数增加 1, 该方法立即返回。(当前线程, 可以多次锁)
 * 3. 如果当前线程获取不到这个锁, 那么当前线程排队等待
 */
public class ReLockTest {

    public static void main(String[] args) {
        Garden garden = new Garden();
        //20 个人, 每个人都想进入公园
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    garden.openDoor();  //20个人排队进入公园
                }
            }).start();
        }
    }

    //公园
    static class Garden {

        private ReentrantLock locker = new ReentrantLock();

        /**
         * 需要一定时间, 进入公园的大门 (要验票), 公园入口只能一个个顺序进入
         * 因为要一个个顺序进入, 那么就需要使用排它特性
         */
        public void openDoor() {
            try {
                System.out.println(Thread.currentThread().getId() + " -- 准备进入公园");
                locker.lock();                  //排队获得锁
                locker.lock();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getId() + " -- 已经进入公园");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
                locker.unlock();    //必须在 finally 里解锁, 防止发生异常无法解锁
            }
        }
    }
}
