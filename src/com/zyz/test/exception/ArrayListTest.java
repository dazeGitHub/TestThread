package com.zyz.test.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试 ConcurrentModificationException
 */
public class ArrayListTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(); //ArrayList 集合中没有锁控制
        for(int i = 0; i < 30; ++i){
            list.add(i);
        }

        //一个线程遍历这个 list 集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(Integer aa: list){
                    System.out.println("aa= " + aa);
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();

        //另一个线程修改这个 list 集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    list.remove(i);
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
