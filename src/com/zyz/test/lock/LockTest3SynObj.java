package com.zyz.test.lock;

/**
 * 使用 synchronized 锁 this 对象
 */
public class LockTest3SynObj {

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

        /**
         * 需要一定时间, 进入公园的大门 (要验票), 公园入口只能一个个顺序进入
         * 因为要一个个顺序进入, 那么就需要使用排它特性
         */
        //锁方法线程会在外边排队, 导致 打印 准备进入公园 的日志时, 线程已经进入到公园了
        //所以不要锁方法了, 直接锁方法面积太大了
        public  void openDoor(){
            try{
                System.out.println(Thread.currentThread().getId() + " -- 准备进入公园");
                synchronized (this){ //this 是公园对象
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getId() + " -- 已经进入公园");
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }
        }
    }
}