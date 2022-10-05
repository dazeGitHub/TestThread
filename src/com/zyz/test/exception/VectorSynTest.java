package com.zyz.test.exception;

import java.util.List;
import java.util.Vector;

/**
 * 手动给 Vector 集合加锁
 * 先读取 Vector 的元素, 读取的时候加锁, 但是修改 Vector 的元素时不加锁
 */
public class VectorSynTest {
    
    public static void main(String[] args) {

        List<Integer> list = new Vector<>(); //Vector 中 iterator() 和 remove() 都使用了 synchronized 锁
        for(int i = 0; i < 30; ++i){
            list.add(i);
        }

        //一个线程遍历这个 list 集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list){            //遍历时, 手动锁定 list 对象
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
                    //Vector 的 remove() 方法有 Synchronized, 因此执行 remove() 方法时必须要获得锁
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
