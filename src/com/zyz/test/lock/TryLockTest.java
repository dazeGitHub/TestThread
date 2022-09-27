package com.zyz.test.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTest {

    public static void main(String[] args) {
        Garden garden = new Garden();
        //20 个人, 每个人都想进入公园
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    garden.openDoor();  //20个人排队进入公园
                }
            }).start();
        }
    }

    //公园
    static class Garden {

        private ReentrantLock locker = new ReentrantLock();

        /**
         * 需要一定时间, 进入公园的大门 (要验票), 公园入口只能一个个顺序进入
         * 因为要一个个顺序进入, 那么就需要使用排它特性
         */
        public void openDoor(){
            try{
                System.out.println(Thread.currentThread().getId() + " -- 准备进入公园");
                //locker.lock();                  //排队获得锁
                boolean bResult = locker.tryLock(3, TimeUnit.SECONDS);
                //如果获得了锁, 那么 bResult 就会为 true
                if(bResult){
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getId() + " -- 已经进入公园");
                }else{
                    System.out.println(Thread.currentThread().getId() + " -- 放弃进入公园");
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    //Exception in thread "Thread-4" ...
                    locker.unlock();    //必须在 finally 里解锁, 防止发生异常无法解锁
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
