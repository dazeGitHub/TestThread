package com.zyz.test.single_instance.hunger;

/**
 * 饿汉式2 : 静态代码块
 */
public class SingletonStaticBlock {
    //单例的特点
    //1. 私有的静态的唯一的当前类的对象
    private static SingletonStaticBlock singleton;

    private SingletonStaticBlock(){
        //2. 私有构造函数, 防止外面去 new 这个 Class
        System.out.println("SingletonStaticBlock, Thread id = " + Thread.currentThread().getId());
    }

    static {
        singleton = new SingletonStaticBlock(); //用于初始化 static 变量, 而且只有一次进入的机会
    }

    //3. 使用静态方法返回该 singleton 单例对象
    public static SingletonStaticBlock instance() {
        return singleton;
    }

    public void doSomething(){

    }
}
