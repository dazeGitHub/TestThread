package com.zyz.test.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 使用 ArrayList 但是又想解决 ConcurrentModificationException 异常, 那么就可以使用 Collections 里面的方法
 */
public class CollectionsTest {

    public static void main(String[] args) {

        //List<Integer> list = new ArrayList<>(); //Vector 中 iterator() 和 remove() 都使用了 synchronized 锁
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

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
                    /**
                     * public boolean remove(Object o){
                     *   synchronized(mutex){
                     *      return c.remove(o);
                     *   }
                     * }
                     * remove() 操作时, 必须要获得当前对象的锁, 即 mutex
                     */
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
