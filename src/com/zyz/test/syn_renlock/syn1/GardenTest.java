package com.zyz.test.syn_renlock.syn1;

public class GardenTest {

    public static void main(String[] args) {
        Garden garden = new Garden();  //所有人必须进入的是同一个 Garden

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
