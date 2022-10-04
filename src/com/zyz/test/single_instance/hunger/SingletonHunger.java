package com.zyz.test.single_instance.hunger;

/**
 * 饿汉式1: 直接创建
 */
public class SingletonHunger {
    //单例的特点
    //1. 私有的静态的唯一的当前类的对象
    private static SingletonHunger singleton = new SingletonHunger();

    private SingletonHunger(){
        //2. 私有构造函数, 防止外面去 new 这个 Class
        System.out.println("SingletonHunger,  Thread id = " + Thread.currentThread().getId());
    }

    public static SingletonHunger instance(){
        return singleton;
    }

    public void doSomething(){

    }
}
