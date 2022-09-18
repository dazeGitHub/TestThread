package com.zyz.test.tickets.test;

import com.zyz.test.tickets.bean.Ticket;
import com.zyz.test.tickets.bean.BookTicket;
import com.zyz.test.tickets.bean.BookTicket3;
import com.zyz.test.tickets.bean.BookTicketSyn;

import java.util.LinkedList;

public class TestTrain {

    //方式一 : 三个线程同时卖票, 但是不共享 Runnable
    public static void bookA(){
        new Thread(new BookTicket()).start();
        new Thread(new BookTicket()).start();
        new Thread(new BookTicket()).start();
    }

    //方式二 : 三个线程同时卖票, 共享同一个 Runnable
    //不加锁
    public static void bookB(){
        BookTicket bt = new BookTicket();
        new Thread(bt).start();
        new Thread(bt).start();
        new Thread(bt).start();
    }
    //加锁
    public static void bookC(){
        BookTicketSyn bt = new BookTicketSyn();
        new Thread(bt).start();
        new Thread(bt).start();
        new Thread(bt).start();
    }

    //方式三 : 给三个线程传递共享对象
    public static void bookD(){
        LinkedList<Ticket> allTicket = new LinkedList<Ticket>();
        for(int i = 0; i < 100; i++){
            Ticket ticket = new Ticket("t" + i);
            allTicket.add(ticket);
        }

        long startTime = System.currentTimeMillis();

        //三个线程共享同一块资源 allTicket
        Thread t1 = new BookTicket3(allTicket, startTime);
        t1.start();

        Thread t2 = new BookTicket3(allTicket, startTime);
        t2.start();

        Thread t3 = new BookTicket3(allTicket, startTime);
        t3.start();
    }

    public static void main(String[] args) {
        //TestTrain.bookA();
        //TestTrain.bookB();
        //TestTrain.bookC();
        TestTrain.bookD();
    }
}
