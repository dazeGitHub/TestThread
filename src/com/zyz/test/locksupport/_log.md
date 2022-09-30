
#### TestWait

##### 1. 使用传统的 wait()/notify() 方式实现线程阻塞

###### 1. 不使用 waitObject.wait() 和 waitObject.notify()

```java
//sum = 4950
```

###### 2. 使用 waitObject.wait() 和 waitObject.notify()

直接使用 waitObject.wait() 和 waitObject.notify() 并不能实现阻塞效果, 必须配合 Synchronized 锁, 否则会出现异常 :

```java
//Exception in thread "Thread-0" java.lang.IllegalMonitorStateException: current thread is not owner
//	at java.base/java.lang.Object.wait(Native Method)
//	at java.base/java.lang.Object.wait(Object.java:338)
//	at com.zyz.test.locksupport.TestWait$1.run(TestWait.java:19)
//	at java.base/java.lang.Thread.run(Thread.java:833)
//        
//Exception in thread "main" java.lang.IllegalMonitorStateException: current thread is not owner
//	at java.base/java.lang.Object.notify(Native Method)
//	at com.zyz.test.locksupport.TestWait.main(TestWait.java:33)
```

###### 3. 使用 waitObject.wait() 和 waitObject.notify() + synchronized

```java
//等待 5 秒后打印
//sum = 4950
```

###### 4. 主线程注释 Thread.sleep(5000)

此时主线程先执行 waitObject.notify(), 子线程再执行 waitObject.wait(), 运行后没有产生解锁的效果, 子线程一直被阻塞。

所以必须先调用 wait() 后有 notify()

##### 2. 使用 LockSupport 的 park()/unpark(thread) 方式实现线程阻塞

运行后立刻出现解锁效果, 子线程没有被阻塞

##### 3. 使用 ReentrantLock 的 Condition 的 await() 和 signal() 的方式实现线程阻塞

###### 1. 不使用 rLock.lock() 和 rLock.unlock() 加锁

```java
//Exception in thread "main" java.lang.RuntimeException: java.lang.IllegalMonitorStateException
//	at com.zyz.test.locksupport.TestRWait.main(TestRWait.java:38)
```

###### 2. 使用 rLock.lock() 和 rLock.unlock() 加锁

```java
//等待 5 秒后打印
//sum = 4950
```
