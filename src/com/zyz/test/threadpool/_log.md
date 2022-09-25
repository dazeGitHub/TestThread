
##### 1. CachedThreadPoolTest

使用 CachedThreadPool 线程重用率比较低

```java
//21 is working over
//19 is working over
//24 is working over
//27 is working over
//33 is working over
//22 is working over
//31 is working over
//30 is working over
//23 is working over
//18 is working over
//20 is working over
//28 is working over
//26 is working over
//34 is working over
//25 is working over
//35 is working over
//17 is working over
//16 is working over
//32 is working over
//29 is working over
```

##### 2. FixedThreadPoolTest

使用 FixedThreadPool 来回一直那 3 个线程

```java
//18 is working over
//16 is working over
//17 is working over
//17 is working over
//17 is working over
//
//18 is working over
//16 is working over
//17 is working over
```

##### 3. SingleThreadPool

SingleThreadPoolTest2 中使用 Executors.newSingleThreadExecutor( ) 线程池的方式创建单个线程, 执行 20 个 ListeningTask, 索引 i = 3 的 ListeningTask 会抛出异常,日志如下 :

```java
//16 beginning starting 0
//16 run over... 0
//16 beginning starting 1
//16 run over... 1
//16 beginning starting 2
//16 run over... 2
//16 beginning starting 3
//Exception in thread "pool-1-thread-1" java.lang.RuntimeException: 异常导致程序崩溃......
//	at com.zyz.test.threadpool.task.ListeningTask.run(ListeningTask.java:25)
//	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
//	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
//	at java.base/java.lang.Thread.run(Thread.java:833)
//18 beginning starting 4
//18 run over... 4
//18 beginning starting 5
//18 run over... 5
//18 beginning starting 6
//...
//18 beginning starting 18
//18 run over... 18
//18 beginning starting 19
//18 run over... 19
```

可见一旦任务出现异常, 那么就换一个新线程执行

##### 4. Callable

```java
//MainThread id = 1, value = [ ChildThread id = 16 result is 0 ]
//MainThread id = 1, value = [ ChildThread id = 16 result is 1 ]
//        
//MainThread id = 1, value = [ ChildThread id = 16 result is 18 ]
//MainThread id = 1, value = [ ChildThread id = 16 result is 19 ]
```