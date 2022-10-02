package com.zyz.test.syn_renlock.syn2;

public class GardenTest2 {

    public static void main(String[] args) {
        Garden2 garden = new Garden2();  //所有人必须进入的是同一个 Garden

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
