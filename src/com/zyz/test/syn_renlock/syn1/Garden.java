package com.zyz.test.syn_renlock.syn1;

import com.zyz.test.utils.Log;

/**
 * 1. 期望所有人 陆续进入大门 和 陆续进入屋子, 是有序的, 不能拥挤
 * 2. 使用 synchronized 锁定方法, 表示同一个对象的这个方法, 在多线程并发时, 只能允许一个线程进入 (必须是多人进入同一个房子的大门)
 *
 * 3. 疑问 : 进门 openDoor() 和 进屋子 enterRoom() 之间是否有影响
 *    答案 : 目前的写法, openDoor() 与 enterRoom() 之间, 是相互排斥的
 *          如果有人正在进大门, 那么想进屋子也要等待。同理, 有人正在进屋子, 想进大门也要等待。
 *          然而进入屋子和进入大门不应该有关系, 所以 openDoor() 和 enterRoom() 前都加 synchronized 的写法是有问题的
 *
 *    解释 : Synchronized 同步方法的时候, 线程进入方法体, 同时对当前对象 Garden 加锁。出了同步方法体, 对象解锁。
 *          Synchronized 锁是排它的, 一个线程当进入 openDoor() 时, 就持有了对象锁。其他线程 想 enterRoom() 也必须拿到锁才行
 *          但是对象锁是排它的, 所以另一个线程想 enterRoom() 只能等待。
 *
 *          Synchronized 同步方法就是对当前对象加锁, 所以凡是该对象的 synchronized 修饰的方法,
 *          只要进入了某一个 synchronized 方法, 那么就不能进入其他 synchronized 方法 (即不能给对象重复加锁)
 *
 */
public class Garden {

    public Garden(){

    }

    //进入房子先进入外面的大门
    synchronized public void openDoor(String name){
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --openDoor---start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --openDoor---end");
    }

    synchronized public void enterRoom(String name){
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterRoom---------------start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterRoom---------------end");
    }
}
