
#### 

##### 1. TestReentrant

使用 ReentrantReadWriteLock 后, 可以并发读, 但是不能 并发写 或 并发读写。

###### 写, 写, 读, 读

```java
//线程a Id = 16, 添加了 BookName a0
//线程a Id = 16, 添加了 BookName a1
//线程a Id = 16, 添加了 BookName a2
//
//线程a Id = 16, 添加了 BookName a28
//线程a Id = 16, 添加了 BookName a29
//线程b Id = 17, 添加了 BookName b0
//
//线程b Id = 17, 添加了 BookName b28
//线程b Id = 17, 添加了 BookName b29
//线程x Id = 18, ----- 读取了 BookName a0
//线程y Id = 19, ----- 读取了 BookName a0
//
//线程x Id = 18, ----- 读取了 BookName a29
//线程y Id = 19, ----- 读取了 BookName a29
//线程y Id = 19, ----- 读取了 BookName b0
//线程x Id = 18, ----- 读取了 BookName b0
//
//线程y Id = 19, ----- 读取了 BookName b29
//线程x Id = 18, ----- 读取了 BookName b29
```

##### 2. TestReentrant2

###### 写, 读, 写, 读

调整线程顺序, 并修改 第二次写的线程的 sleep 时间为 500ms, 日志如下:

```java
//线程a Id = 16, 添加了 BookName a0
//线程a Id = 16, 添加了 BookName a1
//线程a Id = 16, 添加了 BookName a2
//
//线程a Id = 16, 添加了 BookName a28
//线程a Id = 16, 添加了 BookName a29
//线程y Id = 19, ----- 读取了 BookName a0
//线程x Id = 17, ----- 读取了 BookName a0
//
//线程y Id = 19, ----- 读取了 BookName a29
//线程x Id = 17, ----- 读取了 BookName a29
//线程b Id = 18, 添加了 BookName b0
//线程b Id = 18, 添加了 BookName b1
//        
//线程b Id = 18, 添加了 BookName b28
//线程b Id = 18, 添加了 BookName b29
```

###### 读时不使用 ReadLock

将线程 y 的 `locker.readLock().lock();` 和 `locker.readLock().unlock();` 注释掉, 那么在写的过程中 线程y 就可以插队了

日志如下 :

```java
//线程a Id = 16, 添加了 BookName a0
//线程a Id = 16, 添加了 BookName a1
//线程a Id = 16, 添加了 BookName a2
//这里线程y 插队了
//线程y Id = 19, ----- 读取了 BookName a0
//线程a Id = 16, 添加了 BookName a3
//线程y Id = 19, ----- 读取了 BookName a1
//线程a Id = 16, 添加了 BookName a4
//线程y Id = 19, ----- 读取了 BookName a2
//
//线程y Id = 19, ----- 读取了 BookName a26
//线程a Id = 16, 添加了 BookName a29
//        
//线程y Id = 19, ----- 读取了 BookName a28
//线程x Id = 17, ----- 读取了 BookName a1
//线程y Id = 19, ----- 读取了 BookName a29
//线程x Id = 17, ----- 读取了 BookName a2
//
//线程x Id = 17, ----- 读取了 BookName a28
//线程x Id = 17, ----- 读取了 BookName a29
//线程b Id = 18, 添加了 BookName b0
//线程b Id = 18, 添加了 BookName b1
//        
//线程b Id = 18, 添加了 BookName b28
//线程b Id = 18, 添加了 BookName b29
```

