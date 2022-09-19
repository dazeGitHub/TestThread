package com.zyz.test.interactive.thread;

/**
 * thinking in java p627
 */
public class WaitTest {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Waiter waiter = new Waiter(restaurant);
        waiter.start();
        Chef chef = new Chef(restaurant, waiter);
        chef.start();
    }
}

//餐馆
class Restaurant {
    public Order order; //一个 Restaurant 只有一个订单
}

//订单
class Order {
    private static int i = 0; //该变量用来计数
    private int m_count;

    public Order() {
        m_count = i++;
        if (m_count == 10) {
            System.out.println("没有食物了，结束!");
            System.exit(0);
        }
    }
}

//服务员
class Waiter extends Thread {
    private Restaurant m_restaurant;

    public Waiter(Restaurant r) {
        m_restaurant = r;
    }

    public void run() {
        while (m_restaurant.order == null) {
            synchronized (this) {
                try {
                    System.out.println("Waiter Thread id = " + Thread.currentThread().getId() + " waiting !!");
                    wait();  //调用的是当前 Waiter 对象的 wait() 方法      //等待通知，接到通知后才能向下运行
                    m_restaurant.order = null;
                    System.out.println("Waiter Thread id = " + Thread.currentThread().getId() + " 收到通知，取走订单 \n");
                } catch (Exception e) {
                }
            }
        }
    }
}

//厨师
class Chef extends Thread {
    private Restaurant m_restaurant;
    private Waiter m_waiter;

    public Chef(Restaurant restaurant, Waiter waiter) {
        m_restaurant = restaurant;
        m_waiter = waiter;
    }

    public void run() {
        while (true) {
            if (m_restaurant.order == null) {
                m_restaurant.order = new Order();
                System.out.println("厨师 Thread id=" + Thread.currentThread().getId() + ", 接到新订单");

                //notify() 代码必须在一个 synchronized 代码块中执行, 调用哪个共享对象的 notify() 方法 那么对该共享对象加锁
                synchronized (m_waiter) {
                    System.out.println("厨师线程 id=" + Thread.currentThread().getId() + "----通知 waiter 取食物");
                    m_waiter.notify();
                }

                //下面的代码
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }

        }
    }
}

