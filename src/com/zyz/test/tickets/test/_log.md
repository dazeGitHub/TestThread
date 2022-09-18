
##### 1. 调用 TestTrain.bookA( )

日志可见, 车票剩余:99 打印了 3 次, 说明同一张票 (第 100 张票) 卖了 3 次

因为 new 了三个 BookTicket 这样的 Runnable, 所以 tickets 也创建了 3 个, 三个线程互不干扰, 各自运行自己的 run() 方法
这是不正确的, 原因就是三个线程资源没有进行真正的共享

```java
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 17 准备出票
//...
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 17 准备出票
//BookTicket 窗口 Id = 17 车票剩余:99
//BookTicket 窗口 Id = 16 车票剩余:99
//BookTicket 窗口 Id = 18 车票剩余:99
```

##### 2. 调用 TestTrain.bookB( );

此时虽然一张票没有卖 3 次, 但是却发生了数据错乱问题

```java
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 17 准备出票
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 17 车票剩余:98
//BookTicket 窗口 Id = 16 车票剩余:97
//BookTicket 窗口 Id = 18 车票剩余:99
//...
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 17 车票剩余:0
//BookTicket 窗口 Id = 18 车票剩余:-1
```

##### 3. 调用 TestTrain.bookC( );

###### 使用 synchronized (tickets)

使用了 `synchronized (tickets)` 后数据没有发生错乱, 但是最后 tickets 变为了 -1, 日志如下 :

```java
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 16 车票剩余:99
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 16 车票剩余:98
//BookTicket 窗口 Id = 18 车票剩余:97
//...
//BookTicket 窗口 Id = 18 车票剩余:2
//BookTicket 窗口 Id = 17 准备出票
//BookTicket 窗口 Id = 17 车票剩余:1
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 16 车票剩余:0
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 18 车票剩余:-1
```

最后出现 -1 的原因是 Id = 18 的线程刚进入 `while (tickets > 0){}` 
这个判断时 tickets 还为 2, 但是之后被 Id = 17 和 Id = 16 的线程都减 1,
导致最后 Id = 18 的线程进入 `synchronized (tickets){}` 代码块时, tickets
已经变为 0 了, 此时再执行 `tickets = tickets - 1;` 最终导致 tickets = -1

###### 使用 synchronized (this)

使用了 `synchronized (this)` 后数据没有发生错乱, 但是最后 tickets 变为了 -2 :

```java
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 16 车票剩余:99
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 16 车票剩余:98
//BookTicket 窗口 Id = 16 准备出票
//...
//BookTicket 窗口 Id = 16 准备出票
//BookTicket 窗口 Id = 16 车票剩余:0
//BookTicket 窗口 Id = 17 准备出票
//BookTicket 窗口 Id = 17 车票剩余:-1
//BookTicket 窗口 Id = 18 准备出票
//BookTicket 窗口 Id = 18 车票剩余:-2
```

##### 4. 调用 TestTrain.bookD( );

三个线程共享同一块资源 allTicket 链表, 日志如下 :

```java
//BookTicket 窗口 Id = 16 准备出票
//车票剩余:99
//BookTicket 窗口 Id = 16 准备出票
//车票剩余:98
//...
//BookTicket 窗口 Id = 16 准备出票
//车票剩余:2
//BookTicket 窗口 Id = 16 准备出票
//车票剩余:1
//BookTicket 窗口 Id = 16 准备出票
//车票剩余:0
```

