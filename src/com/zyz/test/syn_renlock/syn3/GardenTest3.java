package com.zyz.test.syn_renlock.syn3;

public class GardenTest3 {

    public static void main(String[] args) {
        Garden3 garden = new Garden3();  //所有人必须进入的是同一个 Garden

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i ++){ //模拟 100 个人
                    String name = "a" + i;
                    garden.openDoor(name);
                    garden.enterKiosk(name);
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
                    garden.enterKiosk(name);
                    garden.enterRoom(name);
                }
            }
        }).start();
    }
}
