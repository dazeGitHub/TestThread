package com.zyz.test.exception;

import java.util.Vector;
import java.util.List;

public class VectorTest {
    
    public static void main(String[] args) {

        List<Integer> list = new Vector<>(); //Vector 中 iterator() 和 remove() 都使用了 synchronized 锁
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
