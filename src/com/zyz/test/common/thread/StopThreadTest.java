package com.zyz.test.common.thread;

public class StopThreadTest implements Runnable{

    private boolean stopFlag = true;

    @Override
    public void run() {
        int i = 0;
        while (stopFlag){
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
            i++;
            System.out.println("子线程 : " + Thread.currentThread().getName() + "----- running i = " + i);
        }
        System.out.println("----- 子线程结束 -----");
    }

    public void stopThread(){
        stopFlag = false;
    }

    public static void main(String[] args) {
        int index = 0;
        StopThreadTest threadStop = new StopThreadTest();
        new Thread(threadStop).start();
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            index++;
            System.out.println("主线程 : " + Thread.currentThread().getName() + "----- running index = " + index);
            if(index == 10){
                threadStop.stopThread();
            }
        }
    }
}
