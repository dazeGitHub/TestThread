package com.zyz.test.tickets.thread;

/**
 * 使用锁
 */
public class BookTicketSyn implements Runnable{

    //private int tickets = 100;
    private Integer tickets = 100;

    @Override
    public void run() {
        while (tickets > 0){
            //如果使用 tickets 而不是使用 this, 那么会提示 :
            //int is not a valid type's argument for the synchronized statement
            //因为 synchronized() 不能锁基本类型的数据, 只能锁引用对象, 所以要将 tickets 的类型改为 Integer

            //修改 tickets 前先加一把锁, 使用锁来锁这个代码块
            //synchronized (tickets){
            synchronized(this){
                //在单位时间只能有一个线程进入这块代码, 其他线程需要在代码块外排队
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
}
