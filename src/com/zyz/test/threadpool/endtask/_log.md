
##### 1. ThreadPoolEndTaskTest2

```java
// 16--- is running
// 16--- is running
// 16--- is running
// ...
// 16--- is running
// 17--- is running
// 18--- is running
// 发出关闭线程任务的指令...
```

##### 2. ThreadPoolEndTaskTest3

###### 1. 只调用 WorkTask 对象的 wait( ) 方法

```java
//16--- is running
//16--- is running
//17--- is running
//...
```

###### 2. 调用 WorkTask 对象的 wait() 方法 + synchronized

在 WorkTask 的 run() 方法内调用 `synchronized (this){ this.wait(); }` 成功实现了线程等待效果, 并且这些线程是不占用 CPU 资源的

```java
//16--- is running
//17--- is running
//17--- is running
//19--- is running
//16--- is running
//发出暂停执行线程任务的指令...
//19 start waiting...
//18 start waiting...
//...
```

###### 3. 调用 WorkTask 对象的 wait() 和 notify() 方法 + synchronized

```java
//24--- is running
//23--- is running
//发出暂停执行线程任务的指令...
//开始间隔 8 秒
//25 start waiting...
//21 start waiting...
//19 start waiting...
//20 start waiting...
//22 start waiting...
//17 start waiting...
//18 start waiting...
//16 start waiting...
//23 start waiting...
//24 start waiting...
//发出恢复执行线程任务的指令...
//22--- is running
//20--- is running
//23--- is running
```

可见成功实现了 线程等待 和 线程取消等待 的效果

##### 3. ThreadPoolInterruptTest

###### 1. 子线程 阻塞/等待状态 收到 InterruptedException 实现中断

###### 子线程使用 Thread.sleep()

aa 应当运行到 50, 结果 aa 到了 20 时被 bb 中断了发生了 InterruptedException, 此后 aa 就再也没有输出

```java
//16 --aa-- , i = 0
//1--bb-- , i = 0
//1--bb-- , i = 1
//16 --aa-- , i = 1
//1--bb-- , i = 2
//...
//16 --aa-- , i = 20
//1--bb-- , i = 20
//------- 发出 aa 中断指令 --------
//java.lang.InterruptedException: sleep interrupted
//at java.base/java.lang.Thread.sleep(Native Method)
//at com.zyz.test.threadpool.endtask.ThreadPoolInterruptTest$1.run(ThreadPoolInterruptTest.java:16)
//at java.base/java.lang.Thread.run(Thread.java:833)
//1--bb-- , i = 21
//1--bb-- , i = 22
//1--bb-- , i = 23
//...
//1--bb-- , i = 47
//1--bb-- , i = 48
//1--bb-- , i = 49
```

###### 子线程 不使用 Thread.sleep(), 使用大型浮点运算

aa 在运行过程中, bb 发出了 "aa 中断指令", 但是 aa 并没有中断, 也没有发生 InterruptedException, 继续照常执行。

```java
//1--bb-- , i = 0
//16 --aa-- , i = 0
//1--bb-- , i = 1
//1--bb-- , i = 2
//
//1--bb-- , i = 20
//------- 发出 aa 中断指令 --------
//1--bb-- , i = 21
//1--bb-- , i = 22
//16 --aa-- , i = 4
//1--bb-- , i = 23
```

除非 aa 再加上 `Thread.sleep();` 方法才会再发生 InterruptedException 异常。
即如果当前线程没有处于 blocking 状态, 则不会发生中断异常。

##### 4. ThreadPoolInterruptTest2

#### 子线程 非 阻塞/等待状态 使用 isInterrupted( ) 判断是否发生中断

```java
//16 --aa--, i = 763594
//16 --aa--, i = 763595
//...
//16 --aa--, i = 811231
//------- 发出 aa 中断指令 --------
//16 --aa--, i = 811232
//1--bb-- , i = 21
//1--bb-- , i = 22
//...
//1--bb-- , i = 44
//1--bb-- , i = 45
```

##### 5. FutureCancelTaskTest1

###### 使用 future.get() 获取 Callable 的 call() 方法的返回值

```java
//future.get() = [Thread id = 16, taskId is 0]
//future.get() = [Thread id = 16, taskId is 1]
//future.get() = [Thread id = 16, taskId is 2]
//...
//future.get() = [Thread id = 16, taskId is 18]
//future.get() = [Thread id = 16, taskId is 19]
```

##### 6. FutureCancelTaskTest2

###### 使用 future.cancel(true) 取消任务

总共 50 个任务, 但是调用了 future.cancel(true) 后只执行了 13 个任务, futureList 中的任务就被取消了

```java
//Thread id = 16, taskId is 0
//Thread id = 17, taskId is 1
//Thread id = 16, taskId is 2
//Thread id = 17, taskId is 3
//Thread id = 16, taskId is 4
//Thread id = 17, taskId is 5
//Thread id = 16, taskId is 6
//Thread id = 17, taskId is 7
//Thread id = 16, taskId is 8
//Thread id = 17, taskId is 9
//Thread id = 16, taskId is 10
//Thread id = 17, taskId is 11
//Thread id = 16, taskId is 12
//Thread id = 17, taskId is 13
//取消线程任务, start...
```