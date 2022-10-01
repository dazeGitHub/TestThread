
##### 1. JoinTest

###### 注释 m_sleeper.join( );

当注释 `m_sleeper.join();` 方法时, 两个线程都是独立在运行, 互不干扰 :

```java
//Joiner id=17  start run
//Sleeper Thread id = 16   start run
//Joiner Thread id = 17 ---k= 0
//Sleeper Thread id = 16 ---i = 0
//Joiner Thread id = 17 ---k= 1
//Sleeper Thread id = 16 ---i = 1
//Sleeper Thread id = 16 ---i = 2
//
//Sleeper Thread id = 16 ---i = 48
//Joiner Thread id = 17 ---k= 48
//Sleeper Thread id = 16 ---i = 49
//Sleeper Thread id = 16   end
//Joiner Thread id = 17 ---k= 49
//Joiner Thread id=17  end
```

###### 不注释 m_sleeper.join( );

不注释 `m_sleeper.join();` 方法时, Joiner 线程会等待 Sleeper 线程执行完

```java
//Sleeper Thread id = 16   start run
//Joiner id=17  start run
//Sleeper Thread id = 16 ---i = 0
//Joiner Thread id = 17 ---k= 0
//...
//Joiner Thread id = 17 ---k= 5
//Sleeper 准备加入到当前线程线程 Thread id = 17

//Sleeper Thread id = 16 ---i = 6
//Sleeper Thread id = 16 ---i = 7
//... 
//Sleeper Thread id = 16 ---i = 49
//Sleeper Thread id = 16   end

//Joiner Thread id = 17 ---k= 6
//Joiner Thread id = 17 ---k= 7
//...
//Joiner Thread id = 17 ---k= 48
//Joiner Thread id = 17 ---k= 49
//Joiner Thread id=17  end
```

##### 2. WaitTest

```java
//Waiter Thread id = 16 waiting !!
//厨师 Thread id=17, 接到新订单
//厨师线程 id=17----通知 waiter 取食物
//Waiter Thread id = 16 收到通知，取走订单 
//...
//Waiter Thread id = 16 waiting !!
//厨师 Thread id=17, 接到新订单
//厨师线程 id=17----通知 waiter 取食物
//Waiter Thread id = 16 收到通知，取走订单 
```

##### 3. DieLock

```java
//left ChopStick 1  waiting for  right ChopStick 2
//left ChopStick 2  waiting for  right ChopStick 3
```

##### 4. SemaphoreTest

```java
//Thread id = 19----- num = 3 开始实验 ........
//Thread id = 20----- num = 4 开始实验 ........
//Thread id = 17----- num = 1 开始实验 ........
//Thread id = 18----- num = 2 开始实验 ........
//Thread id = 16----- num = 0 开始实验 ........
//Thread id = 19 ------ num = 3 ,实验完毕
//Thread id = 23----- num = 7 开始实验 ........
//Thread id = 17 ------ num = 1 ,实验完毕
//Thread id = 20 ------ num = 4 ,实验完毕
//Thread id = 24----- num = 8 开始实验 ........
//Thread id = 22----- num = 6 开始实验 ........
//Thread id = 16 ------ num = 0 ,实验完毕
//Thread id = 18 ------ num = 2 ,实验完毕
//
//Thread id = 33----- num = 17 开始实验 ........
//Thread id = 34----- num = 18 开始实验 ........
//Thread id = 35 ------ num = 19 ,实验完毕
//Thread id = 21----- num = 5 开始实验 ........
//Thread id = 29 ------ num = 13 ,实验完毕
//Thread id = 30 ------ num = 14 ,实验完毕
//Thread id = 33 ------ num = 17 ,实验完毕
//Thread id = 34 ------ num = 18 ,实验完毕
//Thread id = 21 ------ num = 5 ,实验完毕
```

##### 5. CountDownLatchTest

```java
//Thread id = 16 正在等待其他任务完成...
//Thread id = 20 任务完成
//Thread id = 26 任务完成
//Thread id = 18 任务完成
//Thread id = 23 任务完成
//Thread id = 24 任务完成
//Thread id = 22 任务完成
//Thread id = 17 任务完成
//Thread id = 19 任务完成
//Thread id = 21 任务完成
//Thread id = 25 任务完成
//Thread id = 16 汽车总装任务完成...
```