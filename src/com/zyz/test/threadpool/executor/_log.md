
#### 1. ThreadPoolExecutorTest (LinkedBlockingQueue)

##### 1. 核心线程数为 1, 最大线程数为 3, 10 个任务, 队列不限制容量

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

##### 2. 核心线程数为 1, 最大线程数为 3, 10 个任务, 队列限制容量 5

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

#### 2. ThreadPoolExecutorTest2 (SynchronousQueue)

##### 1. 核心线程数为 1, 最大线程数为 5, 10 个任务, 队列限制容量 0

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

##### 2. 核心线程数为 1, 最大线程数为 10, 10 个任务, 队列限制容量 0

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