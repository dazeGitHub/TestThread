
##### ProductConsumerTest

这种 一个生产者多个消费者 就是 生产者 - 消费者 模式, 这种场景使用 BlockingQueue 就很适合

```java
//16-- 插入队列 --0
//16-- 插入队列 --1
//16-- 插入队列 --2
//        
//16-- 插入队列 --37
//17 ------------ 取出 --- 0
//16-- 插入队列 --38
//16-- 插入队列 --39
//18 ------------ 取出 --- 1
//
//18 ------------ 取出 --- 86
//16-- 插入队列 --99
//17 ------------ 取出 --- 87
//17 ------------ 取出 --- 98
//18 ------------ 取出 --- 99
```

##### SynchronizedQueueTest2

```java
//16 is Running, i = 0
//17 is Running, i = 1
//18 is Running, i = 2
//19 is Running, i = 3
//16 is Running, i = 4
//17 is Running, i = 5
//18 is Running, i = 6
//19 is Running, i = 7
//16 is Running, i = 8
//17 is Running, i = 9
```