package com.zyz.test.syn_renlock.renlock;

import com.zyz.test.utils.Log;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 期望所有人 陆续进入大门 和 陆续进入屋子, 是有序的, 不能拥挤
 * 使用 ReentrantLock 解决 使用 Synchronized 为方法加锁导致 一个线程 openDoor 另一个线程却不能 openRoom 的问题
 *
 *  总结 : 使用两把锁 doorLock 与 roomLock, 分别锁大门和屋子, 则 openDoor() 与 enterRoom() 之间没有任何影响。
 */
public class Garden3_3 {

    //它也是排他锁, 即一个线程持有了锁, 另一个线程就无法持有锁
    ReentrantLock doorLock = new ReentrantLock();
    ReentrantLock roomLock = new ReentrantLock();

    public Garden3_3() {

    }

    //进入房子先进入外面的大门
    public void openDoor(String name) {
        doorLock.lock();
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --openDoor---start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --openDoor---end");
            doorLock.unlock();
        }
    }

    public void enterRoom(String name) {
        roomLock.lock();
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterRoom---------------start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterRoom---------------end");
            roomLock.unlock();
        }
    }
}
