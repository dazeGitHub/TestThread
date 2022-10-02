package com.zyz.test.syn_renlock.syn3;

import com.zyz.test.utils.Log;

/**
 * 进亭子
 * 锁了对象, 但是对象的方法没有加 Synchronized, 对象加了锁不会对没有 Synchronized 的该方法产生影响,
 */
public class Garden3 {

    public Garden3(){

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

    /**
     * 亭子用于公共休息场所, 没有门, 无需排队进入, 所以 enterKiosk() 不能有锁
     */
    public void enterKiosk(String name){
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterKiosk ************** start");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.logger.info(name + " --线程 id = " + Thread.currentThread().getId() + " --enterKiosk ************** end");
    }
}
