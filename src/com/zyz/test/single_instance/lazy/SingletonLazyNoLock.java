package com.zyz.test.single_instance.lazy;

/**
 * 懒汉式
 */
public class SingletonLazyNoLock {
    //单例的特点
    //1. 私有的静态的唯一的当前类的对象
    private static SingletonLazyNoLock singleton;

    private SingletonLazyNoLock(){
        //2. 私有构造函数, 防止外面去 new 这个 Class
    }

    //3. 使用静态方法返回该 singleton 单例对象
    public static SingletonLazyNoLock instance() {
        if(singleton == null){
            System.out.println("instance...,  Thread id = " + Thread.currentThread().getId());
            singleton = new SingletonLazyNoLock();
        }
        return singleton;
    }

    public void doSomething(){

    }
}
