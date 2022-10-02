package com.zyz.test.threadpool.endtask;

/**
 * 子线程 非 阻塞/等待状态 使用 isInterrupted( ) 判断是否发生中断
 */
public class ThreadPoolInterruptTest2 {

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    double d = 100.00;
                    int i = 1;
                    while (!Thread.currentThread().isInterrupted()){
                        System.out.println(Thread.currentThread().getId() + " --aa--, i = " + i);
                        d += (Math.PI + Math.E) / i * 0.235;
                        i++;
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
