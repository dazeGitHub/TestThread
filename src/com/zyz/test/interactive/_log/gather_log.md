
#### TestBook

##### 1. 使用 Vector

###### 1. 写, 读 bookTable 数据

只有添加数据的日志而没有读取数据的日志, 因为在 读操作的子线程 使用 `bookTable.get(i)` 读取数据时,
`bookTable.size() == 0`, 所以直接退出循环, 不再读取数据

```java
//线程 Id = 16, 添加了 BookName0
//线程 Id = 16, 添加了 BookName1
//线程 Id = 16, 添加了 BookName2
//...
//线程 Id = 16, 添加了 BookName27
//线程 Id = 16, 添加了 BookName28
//线程 Id = 16, 添加了 BookName29
```

###### 2. 写, sleep + 读  bookTable 数据

```java
//线程 Id = 16, 添加了 BookName0
//线程 Id = 16, 添加了 BookName1
//线程 Id = 16, 添加了 BookName2
//线程 Id = 17, ----- 读取了 BookName0
//线程 Id = 16, 添加了 BookName3
//线程 Id = 17, ----- 读取了 BookName1
//线程 Id = 16, 添加了 BookName4
//
//线程 Id = 16, 添加了 BookName28
//线程 Id = 17, ----- 读取了 BookName25
//线程 Id = 16, 添加了 BookName29
//线程 Id = 17, ----- 读取了 BookName26
//线程 Id = 17, ----- 读取了 BookName27
//线程 Id = 17, ----- 读取了 BookName28
//线程 Id = 17, ----- 读取了 BookName29
```

###### 3. 写,写,读  bookTable 数据

并发操作时, 两个线程使用 Vector 写, 查看 写 和 写 之间是否产生排队的现象

可见两个线程并发写入并没有影响, 读取时能全部读出来是因为 bookTable.size() 
每次循环都会重新计算

```java
//线程a Id = 16, 添加了 BookName a0
//线程b Id = 17, 添加了 BookName b0
//线程a Id = 16, 添加了 BookName a1
//线程b Id = 17, 添加了 BookName b1
//线程a Id = 16, 添加了 BookName a2
//线程b Id = 17, 添加了 BookName b2
//线程x Id = 18, ----- 读取了 BookName a0
//线程b Id = 17, 添加了 BookName b3
//线程a Id = 16, 添加了 BookName a3
//线程x Id = 18, ----- 读取了 BookName b0
//
//线程a Id = 16, 添加了 BookName a29
//线程b Id = 17, 添加了 BookName b29
//线程x Id = 18, ----- 读取了 BookName a13
//线程x Id = 18, ----- 读取了 BookName b14
//
//线程x Id = 18, ----- 读取了 BookName b28
//线程x Id = 18, ----- 读取了 BookName a28
//线程x Id = 18, ----- 读取了 BookName b29
//线程x Id = 18, ----- 读取了 BookName a29
```

##### 2. 使用 ArrayList

使用 Vector 时 写和读 的并发操作 互不影响, 但是使用 ArrayList 也是完全可以的。

```java
//线程 Id = 16, 添加了 BookName0
//线程 Id = 16, 添加了 BookName1
//线程 Id = 16, 添加了 BookName2
//线程 Id = 17, ----- 读取了 BookName0
//线程 Id = 16, 添加了 BookName3
//线程 Id = 17, ----- 读取了 BookName1
```

##### 3. 使用 synchronized 锁对象

```java
//线程a Id = 16, 添加了 BookName a0
//线程a Id = 16, 添加了 BookName a1
//        
//线程a Id = 16, 添加了 BookName a28
//线程a Id = 16, 添加了 BookName a29
//线程x Id = 18, ----- 读取了 BookName a0
//线程x Id = 18, ----- 读取了 BookName a1
//
//线程x Id = 18, ----- 读取了 BookName a29
//线程b Id = 17, 添加了 BookName b0
//线程b Id = 17, 添加了 BookName b1
//
//线程b Id = 17, 添加了 BookName b28
//线程b Id = 17, 添加了 BookName b29
```

