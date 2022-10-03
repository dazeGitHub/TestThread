package com.zyz.test.syn_renlock.syn5;

import com.zyz.test.utils.Log;

/**
 * 使用 synchronized 锁代码块 :
 * 1. 一个线程进入 synchronized 的代码块, 那么其他线程, 不能访问同一个对象的这个代码块
 * 2. openDoor() 与 enterRoom() 都使用 synchronized (this), 则这两个方法会产生互斥效果
 * 3. openDoor() 与 enterRoom() 都使用 synchronized (Garden.class), 则这两个方法仍然会产生互斥效果
 * 4. openDoor() 使用 synchronized (this){}, 而 enterRoom() 使用 synchronized (Garden.class){},
 *    则测试结果显示, 两个方法可以并发进行, 它们没有互斥效果。
 * 5. openDoor() 与 enterRoom() 都使用 synchronized (object) 的方式, 则两个方法会产生互斥效果。
 * 6. openDoor() 使用 synchronized (this){}, 而 enterRoom() 使用 synchronized (object){},
 *    则测试结果显示, 两个方法可以并发进行, 它们没有互斥效果。
 * 7. openDoor() 使用 synchronized (obj1){}, 而 enterRoom() 使用 synchronized (obj2){},
 *    则测试结果显示, 两个方法可以并发进行, 它们没有互斥效果。
 * 8. openDoor() synchronized (name){}, 而 name 是一个形参变量, 则没有起到同步代码块的效果
 */
class Garden {
    Object obj1 = new Object();
    Object obj2 = new Object();

    public Garden() {

    }

    public void openDoor(String name) {
        //Log.logger.info(name + "--线程" + Thread.currentThread().getId() + " --- openDoor ---------- start");
        //1. 方式一
//        synchronized (this) {
//            doSomething();
//        }
        //2. 方式二
//        synchronized(Garden.class){
//
//        }
        //3. 方式三
//        synchronized (obj1){
//
//        }
        synchronized (name) {
            doSomething();
        }
        //Log.logger.info(name + "--线程" + Thread.currentThread().getId() + " --- openDoor ----------- end");
    }

    public void enterRoom(String name) {
        //Log.logger.info(name + "--线程" + Thread.currentThread().getId() + " *** enterRoom ******* start");
//        synchronized (this) {
//            doSomething();
//        }
        synchronized (name) {
            doSomething();
        }
        //Log.logger.info(name + "--线程" + Thread.currentThread().getId() + " *** enterRoom ******* end");
    }

    private void doSomething(){
        Log.logger.info("--线程" + Thread.currentThread().getId() + " --- doSomething ---------- start");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        Log.logger.info("--线程" + Thread.currentThread().getId() + " --- doSomething ---------- end");
    }
}
