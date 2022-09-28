package com.zyz.test.thread_syn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileTest {
    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());            //创建唯一对象 EvenGenerator
    }
}

class EvenChecker implements Runnable {
    private IntGenerator generator;

    public EvenChecker(IntGenerator g) {
        generator = g;
    }

    public void run() {
        //2. 如果一旦有任何一个线程的 Runnable 检测出来 generator 被取消了, 那么就不再执行 while 循环
        while (!generator.isCanceled()) {
            int val = generator.next();
            //System.out.println("generator.next() val = " + val);
            //1. 如果有任何一个线程的 Runnable 检测出来 val 不是偶数, 那么直接取消 generator
            if (val % 2 != 0) {
                System.out.println("val is " + val + " not even!");
                generator.cancel();                    // Cancels all EvenCheckers
            }
        }
    }

    // Test any type of IntGenerator:
    public static void test(IntGenerator gp) {
        System.out.println("Press Control-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new EvenChecker(gp));     //10 个 EvenChecker 对象，共享一个 EvenGenerator 对象
        }
        exec.shutdown();
    }
}

class EvenGenerator extends IntGenerator {

    //The variable xx is volatile to try to ensure that no compiler optimizations are performed
    private volatile int currentEvenValue = 0;

    //期望每次调用next()的结果都是偶数  //synchronized
    public int next() {
        ++currentEvenValue;                                // Danger point here!
        ++currentEvenValue;
        return currentEvenValue;
    }
}

abstract class IntGenerator {

    //The canceled flag is also volatile in order to ensure visibility(某个线程的修改，其它线程可以立即得到结果)
    private volatile boolean canceled = false;

    public abstract int next();

    // Allow this to be canceled:
    //如果取消, 那么不再进行 next() 向下运算了
    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}


