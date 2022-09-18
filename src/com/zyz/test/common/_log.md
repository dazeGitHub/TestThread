
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
