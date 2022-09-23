package com.zyz.test.interactive.test;

import com.zyz.test.interactive.gather.Book;
import com.zyz.test.interactive.gather.BookTable;

import java.util.Collections;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用 Synchronized 锁对象 而不是 锁代码块
 */
public class TestBookSyncObj {

    //开启两个线程对 BookTable 对象进行操作, 一个线程进行读, 一个线程进行写
    public static void main(String[] args) {
        BookTable bookTable = new BookTable();

        //三个线程都是操作的同一个 bookTable
        //1. 写操作
        new Thread(() -> {
            //只要一个线程对 bookTable 加锁了, 其他线程就无法再获得这个 bookTable 的锁, 因为 synchronized 是排他锁
            synchronized (bookTable) {
                for (int i = 0; i < 30; i++) {
                    Book bk = new Book("BookName a" + Integer.toString(i));
                    bookTable.add(bk);
                    System.out.println("线程a Id = " + Thread.currentThread().getId() + ", 添加了 " + bk.getBname());
                    try {
                        //加一个延时是为了看看在并发时读写 bookTable 里的集合是否受影响
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        //2. 再增加一个写操作
        new Thread(() -> {
            synchronized (bookTable) {
                for (int i = 0; i < 30; i++) {
                    Book bk = new Book("BookName b" + Integer.toString(i));
                    bookTable.add(bk);
                    System.out.println("线程b Id = " + Thread.currentThread().getId() + ", 添加了 " + bk.getBname());
                    try {
                        //加一个延时是为了看看在并发时读写 bookTable 里的集合是否受影响
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        //3. 读操作
        new Thread(() -> {
            //等写操作的线程先写几条数据再读取
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (bookTable) {
                //当前线程使用 bookTable.get(i) 读取数据时, bookTable.size() == 0, 所以直接退出循环, 不再读取数据
                //导致日志中只有写操作的线程的日志
                for (int i = 0; i < bookTable.size(); i++) {
                    Book bk = bookTable.get(i);
                    System.out.println("线程x Id = " + Thread.currentThread().getId() + ", ----- 读取了 " + bk.getBname());
                    try {
                        //加一个延时是为了看看在并发时读写 bookTable 里的集合是否受影响
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}
