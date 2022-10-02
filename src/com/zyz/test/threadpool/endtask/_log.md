
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
