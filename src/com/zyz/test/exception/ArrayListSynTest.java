package com.zyz.test.exception;

import java.util.ArrayList;
import java.util.List;

public class ArrayListSynTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(); //ArrayList 集合中没有锁控制
        for(int i = 0; i < 30; ++i){
            list.add(i);
        }

        //一个线程遍历这个 list 集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list){
                    for(Integer aa: list){
                        System.out.println("aa= " + aa);
                        try {
                            Thread.sleep(200);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }).start();

        //另一个线程修改这个 list 集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    //分析, list 对象被锁定后, remove() 方法不受影响, 因为 ArrayList 的 remove() 没有 Synchronized 修饰
                    synchronized (list){
                        list.remove(i);
                    }
                    System.out.println("remove: " + i);
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
    }
}
