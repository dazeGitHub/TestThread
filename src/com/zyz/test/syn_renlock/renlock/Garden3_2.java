package com.zyz.test.syn_renlock.renlock;

import com.zyz.test.utils.Log;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 期望所有人 陆续进入大门 和 陆续进入屋子, 是有序的, 不能拥挤
 * 使用 ReentrantLock 解决 使用 Synchronized 为方法加锁导致 一个线程 openDoor 另一个线程却不能 openRoom 的问题
 *
 *  总结 : 如果 openDoor() 与 enterRoom() 两个方法使用一把锁 ReentrantLock, 则代码执行效果与使用 synchronized 同步两个方法
 *  的效果完全相同。
 *     但是两者操作的原理完全不同。
 */
public class Garden3_2 {

    //它也是排他锁, 即一个线程持有了锁, 另一个线程就无法持有锁
    //一个线程进入 openDoor() 后, 其他线程有在 openDoor() 排队的, 有在 enterRoom() 排队的
    //openDoor() 和 enterRoom() 共用一个 ReentrantLock 对象, 效果与使用 synchronized 为 openDoor() 和 enterRoom() 加锁相同
    ReentrantLock lock = new ReentrantLock();

    public Garden3_2() {

    }

    //进入房子先进入外面的大门
    public void openDoor(String name) {
        lock.lock();
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --openDoor---start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --openDoor---end");
            lock.unlock();
        }
    }

    public void enterRoom(String name) {
        lock.lock();
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterRoom---------------start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterRoom---------------end");
            lock.unlock();
        }
    }
}
