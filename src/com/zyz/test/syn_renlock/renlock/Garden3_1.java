package com.zyz.test.syn_renlock.renlock;

import com.zyz.test.utils.Log;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 期望所有人 陆续进入大门 和 陆续进入屋子, 是有序的, 不能拥挤
 * 使用 ReentrantLock 解决 使用 Synchronized 为方法加锁导致 一个线程 openDoor 另一个线程却不能 openRoom 的问题
 */
public class Garden3_1 {

    public Garden3_1() {

    }

    //进入房子先进入外面的大门
    public void openDoor(String name) {
        //第一个线程调用 openDoor() 时线程栈会产生一个 lock, 第二个线程调用 openDoor() 时线程栈又会产生一个新的 lock
        //由于第一个线程的 openDoor() 和 第二个线程的 openDoor() 使用的不是一个 lock 对象, 所以根本无法保证两个线程不会同时进入 openDoor()
        ReentrantLock lock = new ReentrantLock();
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
        ReentrantLock lock = new ReentrantLock();
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
