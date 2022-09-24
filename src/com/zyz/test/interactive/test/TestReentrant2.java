package com.zyz.test.interactive.test;

import com.zyz.test.interactive.gather.Book;
import com.zyz.test.interactive.gather.BookTable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 测试使用 ReentrantReadWriteLock 来实现共享读 (一个线程读取时另一个线程也能读取)
 * 使用 lock 进行读写分离, 顺序执行; 读操作是可以共享的, 写操作是不能共享的
 */
public class TestReentrant2 {

    //开启两个线程对 BookTable 对象进行操作, 一个线程进行读, 一个线程进行写
    public static void main(String[] args) {
        BookTable bookTable = new BookTable();
        ReadWriteLock locker = new ReentrantReadWriteLock();

        //三个线程都是操作的同一个 bookTable
        //1. 写操作
        new Thread(() -> {
            //只要一个线程对 bookTable 加锁了, 其他线程就无法再获得这个 bookTable 的锁, 因为 synchronized 是排他锁
            locker.writeLock().lock();
            //synchronized (bookTable) {
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
            //}
            locker.writeLock().unlock();
        }).start();

        //3. 读操作
        new Thread(() -> {
            //等写操作的线程先写几条数据再读取
            try {
                Thread.sleep(100); //等待时间改为 100ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            locker.readLock().lock();
            //synchronized (bookTable) {
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
            //}
            locker.readLock().unlock();
        }).start();

        //2. 再增加一个写操作
        new Thread(() -> {
            try {
                Thread.sleep(500); //再写的等待时间也改为 200ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            locker.writeLock().lock();
            //synchronized (bookTable) {
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
            //}
            locker.writeLock().unlock();
        }).start();

        //4. 读操作
        new Thread(() -> {
            //等写操作的线程先写几条数据再读取
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //将 lock 注释掉, 那么在写的过程中 线程y 就可以插队了
            //locker.readLock().lock();
            //synchronized (bookTable) {
            //当前线程使用 bookTable.get(i) 读取数据时, bookTable.size() == 0, 所以直接退出循环, 不再读取数据
            //导致日志中只有写操作的线程的日志
            for (int i = 0; i < bookTable.size(); i++) {
                Book bk = bookTable.get(i);
                System.out.println("线程y Id = " + Thread.currentThread().getId() + ", ----- 读取了 " + bk.getBname());
                try {
                    //加一个延时是为了看看在并发时读写 bookTable 里的集合是否受影响
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //}
            //locker.readLock().unlock();
        }).start();
    }
}
