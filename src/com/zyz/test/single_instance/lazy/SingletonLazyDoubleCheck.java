package com.zyz.test.single_instance.lazy;

/**
 * 懒汉式 : 双重校验锁
 */
public class SingletonLazyDoubleCheck {
    //单例的特点
    //1. 私有的静态的唯一的当前类的对象
    private volatile static SingletonLazyDoubleCheck singleton;

    private SingletonLazyDoubleCheck(){
        //2. 私有构造函数, 防止外面去 new 这个 Class
    }

    //3. 使用静态方法返回该 singleton 单例对象
     public static SingletonLazyDoubleCheck instance() {
         if (singleton == null) {
             //因为是静态方法, 所以不能锁 this, 只能锁 Class
             synchronized (SingletonLazyDoubleCheck.class) {
                 if (singleton == null) {
                     System.out.println("instance...,  Thread id = " + Thread.currentThread().getId());
                     singleton = new SingletonLazyDoubleCheck();
                 }
             }
         }
        return singleton;
    }

    public void doSomething(){

    }
}
