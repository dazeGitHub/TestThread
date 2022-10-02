package com.zyz.test.syn_renlock.renlock;

/**
 * 使用 ReentrantLock
 */
public class GardenTest3 {

    public static void main(String[] args) {
        //Garden3 garden = new Garden3();    //所有人必须进入的是同一个 Garden
        //Garden3_2 garden = new Garden3_2();
        Garden3_3 garden = new Garden3_3();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i ++){ //模拟 100 个人
                    String name = "a" + i;
                    garden.openDoor(name);
                    garden.enterRoom(name);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i ++){
                    String name = "b" + i;
                    garden.openDoor(name);
                    garden.enterRoom(name);
                }
            }
        }).start();
    }
}
