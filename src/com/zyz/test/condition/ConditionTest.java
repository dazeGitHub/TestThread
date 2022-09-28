package com.zyz.test.condition;

import com.zyz.test.utils.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConditionTest {
    public static void main(String[] args) {
        BoundedBuffer boundedBuffer = new BoundedBuffer();
        ExecutorService exec = Executors.newFixedThreadPool(3);

        //用三个线程同时往 buffer 里写数据
        for(int i = 0; i < 3; i++){
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    //每个任务都往 buffer 中不停的写数据
                    while (true){
                        int random = (int)(Math.random() * 10);
                        try {
                            boundedBuffer.put(new Integer(random)); //3 个线程不停顿的向同一个 buffer 中写数据
                            Thread.sleep(500);
                        }catch (Exception e){

                        }
                    }
                }
            });
        }
        exec.shutdown();

        //主线程隔一段时间后取数据
        try{
            Thread.sleep(8000);
            Object obj = boundedBuffer.take();  //主线程, 取出一条数据
            Log.logger.info(Thread.currentThread().getId() + "---主线程得到了数据--" + obj.toString());
        } catch (Exception e) {

        }
    }
}
