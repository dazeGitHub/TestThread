package com.zyz.test.threadpool.endtask;

/**
 * 子线程 阻塞/等待状态 收到 InterruptedException 实现中断
 */
public class ThreadPoolInterruptTest {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 50; i++){
                        System.out.println(Thread.currentThread().getId() + " --aa-- , i = " + i);
                        //1. 使用 Thread.sleep(100) 会出现 InterruptedException 异常
                        //Thread.sleep(100);

                        //2. 不使用 Thread.sleep(), 而是使用大型的浮点运算, 不会出现 InterruptedException 异常
                        double d = 0;
                        for(int k = 0; k < 100000000; k++){
                            d += (Math.PI + Math.E) / (double) i;
                        }
                        //如果当前线程没有处于 blocking 状态, 则不会发生中断异常。
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        for(int i = 0; i < 50; i++){
            System.out.println(Thread.currentThread().getId() + "--bb-- , i = " + i);
            if(i == 20){
                System.out.println("------- 发出 aa 中断指令 --------");
                thread.interrupt();
            }
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
