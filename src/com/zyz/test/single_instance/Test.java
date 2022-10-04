package com.zyz.test.single_instance;

import com.zyz.test.single_instance.hunger.SingletonStaticBlock;
import com.zyz.test.single_instance.lazy.SingletonLazyDoubleCheck;
import com.zyz.test.single_instance.lazy.SingletonLazySynFunc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        //Singleton s1 = new Singleton();       //Singleton 的构造器私有, 直接 new 会报错

        //测试单线程
        //1. 不加锁的懒汉式单例模式
//        SingletonLazyNoLock s1 = SingletonLazyNoLock.instance();    //直接调用 Singleton 的静态方法来获取对象
//        SingletonLazyNoLock s2 = SingletonLazyNoLock.instance();
//        SingletonLazyNoLock s3 = SingletonLazyNoLock.instance();

        //测试多线程
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; ++i){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    //2. 不加锁的懒汉式单例模式
                    //SingletonLazyNoLock singleton = SingletonLazyNoLock.instance();

                    //3. 直接创建对象的饿汉式单例模式
                    //SingletonHunger singleton = SingletonHunger.instance();
                    //singleton.doSomething();

                    //4. 静态代码块中创建对象的饿汉式单例模式
                    //SingletonStaticBlock singleton = SingletonStaticBlock.instance();
                    //singleton.doSomething();

                    //5. 给 instance() 方法直接加锁的懒汉式单例模式
                    //SingletonLazySynFunc singleton = SingletonLazySynFunc.instance();
                    //singleton.doSomething();

                    //6. 锁 Class 对象的懒汉式单例模式
                    SingletonLazyDoubleCheck singleton = SingletonLazyDoubleCheck.instance();
                    singleton.doSomething();
                }
            });
        }
        pool.shutdown();
    }
}
