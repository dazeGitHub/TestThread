package com.zyz.test.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReLockTest3Syn {

    public static void main(String[] args) {
        GameRoom gameRoom = new GameRoom();
        //20 个人, 每个人都想进入公园
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    gameRoom.openDoor(); //玩游戏要排队进入游戏厅的大门
                }
            }).start();
        }
    }

    //只能一个人进入游戏厅, 游戏厅中有三个方法(三种行为), 进入后可以选择玩不同的游戏
    static class GameRoom {

        private final Object obj = new Object();

        //当前线程只要执行了 openDoor(), 那么 shooting() 和 boating() 也具有排他性, 别的线程也无法执行
        public void openDoor() {
            System.out.println(Thread.currentThread().getId() + " -- 准备进入游戏厅");
            synchronized (obj) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getId() + " -- 已经进入游戏厅 ......");
                //开始玩游戏(随机选择玩哪个游戏)
                int random = (int) (Math.random() * 10);
                if (random % 2 == 0) {
                    shooting();     //玩射击游戏
                } else {
                    boating();      //玩飞船游戏
                    shooting();
                }
            }
            System.out.println(Thread.currentThread().getId() + " -- 已经退出游戏厅");
        }

        public void shooting() {
            synchronized (obj) {
                try {
                    Thread.sleep(1500);
                    System.out.println(Thread.currentThread().getId() + " -- play shooting...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void boating() {
            synchronized (obj) {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getId() + " -- play boating...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
