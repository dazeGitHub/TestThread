package com.zyz.test.syn_renlock.syn7;

import com.zyz.test.utils.Log;

/**
 * 静态方法使用 synchronized, 当一个线程进入这个方法如 m1() 后, 本质是要给这个 Class 加锁。
 * 其他线程, 想进入 m1() 或者 m2(), 都需要先获得这个 Class 锁, 获得锁之后才能进入, 否则就排队等待。
 * 因此 m1() 与 m2() 之间是互斥的。
 *
 * m3() 与 m4(), 如果属于同一个对象, 两个方法都是给这个对象加锁, 他们之间是互斥的
 *
 * m1() 和 m2() 给 SynStaticTool2 类加锁, m3() 与 m4() 是给对象加锁, 它们相互之间是并行关系(不是互斥关系), 不受干扰!
 *
 * m1() 和 m2() 给 SynStaticTool2 类加锁, m3() 与 m4() 也是给 SynStaticTool2 类加锁, 因此 m1(), m2(), m5(), m6() 彼此之间互斥
 */
public class SynStaticTool2 {

    synchronized public static void m1(){
        Log.logger.info("m1 ... start");
        try{
            Thread.sleep(2000);
        }catch (Exception e){
        }
        Log.logger.info("m1 ... end");
    }

    synchronized public static void m2(){
        Log.logger.info("m2 ************* start");
        try{
            Thread.sleep(2000);
        }catch (Exception e){
        }
        Log.logger.info("m2 ************* end");
    }

    synchronized public void m3(){
        Log.logger.info("m3 ++++++++ start");
        try{
            Thread.sleep(2000);
        }catch (Exception e){
        }
        Log.logger.info("m3 ++++++++ end");
    }

    synchronized public void m4(){
        Log.logger.info("m4 &&&&&&&&&&&&& start");
        try{
            Thread.sleep(2000);
        }catch (Exception e){
        }
        Log.logger.info("m4 &&&&&&&&&&&&& end");
    }

    //m5() 和 m6() 是成员方法, 但是锁的是当前 Class 类
    public void m5(){
        synchronized (SynStaticTool2.class){
            Log.logger.info("m5 @@@@@@ start");
            try{
                Thread.sleep(2000);
            }catch (Exception e){
            }
            Log.logger.info("m5 @@@@@@ end");
        }
    }

    public void m6(){
        synchronized (SynStaticTool2.class){
            Log.logger.info("m6 %%%%%%%%% start");
            try{
                Thread.sleep(2000);
            }catch (Exception e){
            }
            Log.logger.info("m6 %%%%%%%%% end");
        }
    }
}
