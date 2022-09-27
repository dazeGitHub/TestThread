package com.zyz.test.lock;

/**
 * 使用 Synchronized 锁方法 实现 ReentrantLock 的效果
 */
public class LockTest2SynFunc {

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
        public synchronized void openDoor(){
            try{
                System.out.println(Thread.currentThread().getId() + " -- 准备进入公园");

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getId() + " -- 已经进入公园");

            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }
        }
    }
}