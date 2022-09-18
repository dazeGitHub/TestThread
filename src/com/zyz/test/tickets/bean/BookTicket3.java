package com.zyz.test.tickets.bean;

import java.util.LinkedList;

public class BookTicket3 extends Thread{

    private LinkedList<Ticket> allTicket;
    private long startTime;

    public BookTicket3(LinkedList<Ticket> allTicket, long startTime){
        this.allTicket = allTicket;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        while(allTicket.size() > 0){
            synchronized (allTicket) {
                if(allTicket.size() > 0){
                    System.out.println("BookTicket 窗口 Id = " + Thread.currentThread().getId() + " 准备出票");
                    allTicket.removeLast();
                    System.out.println("车票剩余:" + allTicket.size());
                    try {
                        Thread.sleep(200);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        double millis = (double)(System.currentTimeMillis() - startTime) / 1000;
        System.out.println("一共耗时 " + millis + "秒");
    }
}
