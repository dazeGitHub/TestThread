
##### DamonThread

Damon 线程一直循环打印, 但是当主线程退出后 Damon 线程也会退出。

```java
//--------------- in damon thread
//main thread i = 0
//--------------- in damon thread
//main thread i = 1
//--------------- in damon thread
//main thread i = 2
//--------------- in damon thread
//main thread i = 3
//--------------- in damon thread
//main thread i = 4
//--------------- in damon thread
//main thread i = 5
//--------------- in damon thread
//main thread i = 6
//--------------- in damon thread
//main thread i = 7
//--------------- in damon thread
//main thread i = 8
//--------------- in damon thread
//main thread i = 9
//************* main thread end ************
```

##### StopThreadTest

```java
//子线程 : Thread-0----- running i = 1
//主线程 : main----- running index = 1
//主线程 : main----- running index = 2
//...
//主线程 : main----- running index = 8
//主线程 : main----- running index = 9
//子线程 : Thread-0----- running i = 9
//子线程 : Thread-0----- running i = 10
//主线程 : main----- running index = 10
//主线程 : main----- running index = 11
//子线程 : Thread-0----- running i = 11
//----- 子线程结束 -----
```

##### YieldThread

###### 注释了 Thread.currentThread( ).yield( );

```java
//线程 ID17 x = 12
//线程 ID16 x = 14
//线程 ID16 x = 16
//线程 ID16 x = 17
//线程 ID16 x = 18
//线程 ID16 x = 19
//线程 ID16 x = 20
//线程 ID17 x = 15
```

其他线程可能无法抢入

###### 没有注释 Thread.currentThread( ).yield( );

```java
//线程 ID17 x = 0
//线程 ID16 x = 0
//线程 ID17 x = 1
//线程 ID16 x = 3
//线程 ID17 x = 3
//线程 ID17 x = 5
//线程 ID16 x = 5
//线程 ID16 x = 7
```

双方线程有更多的机会抢入