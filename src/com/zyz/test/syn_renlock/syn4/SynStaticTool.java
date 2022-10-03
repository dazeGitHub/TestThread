package com.zyz.test.syn_renlock.syn4;

import com.zyz.test.utils.Log;

/**
 * Synchronized 锁静态方法剖析
 */
public class SynStaticTool {

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
}
