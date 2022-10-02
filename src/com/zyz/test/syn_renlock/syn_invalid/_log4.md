
#### SynTestRunnable

在当前线程, 直接调用 runnable 对象的 m2() 方法, 这个时候 synchronized 还是否有用 ?

如果 此时 synchronized 还有效, 那么当 thread 线程调用 runnable 对象的 m1() 方法并且 Thread.sleep(5000) 时,
当前线程调用 runnable 对象的 m2() 方法应该阻塞。所以预期结果是 :

```java
//m1   b=1000
//m2   b = 2000
//main end, b = 2000
```

##### 1. 主线程直接调用 runnable 的 m2() 方法

```java
//m2   b = 2000
//main end, b = 2000
//m1   b=1000
```

可见并不符合预期结果, 即 主线程直接调用 runnable 的 m2() 方法 时, synchronized 并没有产生作用。

即只有在线程里的 run() 方法中调用 m2() 方法时 synchronized 才会生效, 如果是在当前线程直接用对象去调用而不是通过 run() 方法调用的
synchronized 是不会起作用的

##### 2. 新线程调用 runnable 的 m2() 方法

```java
//main end, b = 100
//这里等了 5 秒...
//m2   b = 2000
//这里等了 5 秒...
//m1   b = 1000
```

可见 m2() 等 5 秒后执行完了, 在等待过程期间 m1() 并没有执行, m2() 执行完了后 m1() 才开始执行, 
产生了阻塞效果, 说明 synchronized 起作用了

