
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

##### 5. ThreadPoolExecutorTest (LinkedBlockingQueue)

###### 1. 核心线程数为 1, 最大线程数为 3, 10 个任务, 队列不限制容量

`new ThreadPoolExecutor(1, 3, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());`

一直是 线程17 在 running, 没有启动其他线程, 因为所有任务全部入队了

```java
//17 running ......
//core:1
//max:3
//active:1
//task end --0
//pool size:1
//queue size:9
//------------------------------------
//...    
//17 running ......
//core:1
//max:3
//active:1
//task end --0
//pool size:1
//queue size:9
//------------------------------------
//...      
//17 running ......
//core:1
//max:3
//active:1
//task end --1
//pool size:1
//queue size:8
//------------------------------------
//...       
//17 running ......
//core:1
//max:3
//active:1
//task end --2
//pool size:1
//queue size:7
//------------------------------------
//...   
//core:1
//max:3
//active:1
//task end --9
//pool size:1
//queue size:0
//------------------------------------
//...
```

###### 2. 核心线程数为 1, 最大线程数为 3, 10 个任务, 队列限制容量 5

最大能执行的任务数 = 核心线程数1 + 队列容量5 + (最大线程数3 - 核心线程数1)
               = 1 + 5 + (3 - 1)
               = 6 + 2 
               = 8

但是共有 10 个任务, 所以运行时会抛异常  java.util.concurrent.RejectedExecutionException

```java
//Exception in thread "main" java.util.concurrent.RejectedExecutionException
//18 running ......
//17 running ......
//19 running ......
//core:1
//max:3
//active:3
//task end --0
//pool size:3
//queue size:5
//------------------------------------
//18 running ......
//19 running ......
//17 running ......
//core:1
//max:3
//active:3
//task end --3
//pool size:3
//queue size:2
//------------------------------------
//...
```

##### 6. ThreadPoolExecutorTest2 (SynchronousQueue)

###### 1. 核心线程数为 1, 最大线程数为 5, 10 个任务, 队列限制容量 0

```java
//Exception in thread "main" java.util.concurrent.RejectedExecutionException:
//21 running ......
//17 running ......
//core:1
//20 running ......
//19 running ......
//18 running ......
//max:5
//active:5
//task end --0
//pool size:5
//queue size:0
//------------------------------------
//...
//
//core:1
//max:5
//active:0
//task end --5
//pool size:5
//queue size:0
//------------------------------------
```

###### 2. 核心线程数为 1, 最大线程数为 10, 10 个任务, 队列限制容量 0

运行不会出异常, 10 个线程正常启动

```java
//23 running ......
//18 running ......
//22 running ......
//26 running ......
//21 running ......
//17 running ......
//24 running ......
//20 running ......
//19 running ......
//25 running ......
//core:1
//max:10
//active:10
//task end --0
//pool size:10
//queue size:0
//------------------------------------
//...
//core:1
//max:10
//active:0
//task end --10
//pool size:0
//queue size:0
//------------------------------------
```