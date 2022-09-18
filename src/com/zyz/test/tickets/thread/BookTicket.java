package com.zyz.test.tickets.thread;

/**
 * 不使用锁
 */
public class BookTicket implements Runnable{

    private int tickets = 100;

    @Override
    public void run() {
        while (tickets > 0){
            System.out.println("BookTicket 窗口 Id = " + Thread.currentThread().getId() + " 准备出票");
            tickets = tickets - 1;
            System.out.println("BookTicket 窗口 Id = " + Thread.currentThread().getId() + " 车票剩余:" + tickets);
            try {
                Thread.sleep(200);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
