
##### 1. ArrayListTest

当一个线程访问 ArrayList 里的元素, 但是另一个线程同时修改 ArrayList 里的元素时, 发生了并发访问异常 ConcurrentModificationException

```java
//aa= 0
//remove: 0
//Exception in thread "Thread-0" java.util.ConcurrentModificationException
//	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
//	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
//	at com.zyz.test.exception.ArrayListTest$1.run(ArrayListTest.java:22)
//	at java.base/java.lang.Thread.run(Thread.java:833)
//remove: 1
//remove: 2
//remove: 3
//remove: 4
//remove: 5
//remove: 6
//remove: 7
//remove: 8
//remove: 9
```

###### 使用 synchronized (list) 为 ArrayList 加锁

```java
//aa= 0
//remove: 0
//Exception in thread "Thread-0" java.util.ConcurrentModificationException
//	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
//	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
//	at com.zyz.test.exception.ArrayListSynTest$1.run(ArrayListSynTest.java:20)
//	at java.base/java.lang.Thread.run(Thread.java:833)
//remove: 1
//remove: 2
//remove: 3
//remove: 4
//remove: 5
//remove: 6
//remove: 7
//remove: 8
//remove: 9
```

使用 synchronized (list) 为 ArrayList 加锁 并没有解决 并发访问异常 ConcurrentModificationException

##### 2. VectorTest

###### 不使用 synchronized (list) 为 Vector 加锁

当一个线程访问 Vector 里的元素, 但是另一个线程同时修改 Vector 里的元素时, 仍然发生了并发访问异常 ConcurrentModificationException

```java
//Exception in thread "Thread-0" java.util.ConcurrentModificationException
//	at java.base/java.util.Vector$Itr.checkForComodification(Vector.java:1292)
//	at java.base/java.util.Vector$Itr.next(Vector.java:1248)
//	at com.zyz.test.exception.VectorTest$1.run(VectorTest.java:19)
//	at java.base/java.lang.Thread.run(Thread.java:833)
//remove: 0
//remove: 1
//remove: 2
//remove: 3
//remove: 4
//remove: 5
//remove: 6
//remove: 7
//remove: 8
//remove: 9
```

###### 使用 synchronized (list) 为 Vector 加锁

```java
//aa= 0
//aa= 1
//aa= 2
//...      
//aa= 28
//aa= 29
//remove: 0
//remove: 1
//...
//remove: 8
//remove: 9
```

成功解决了 并发访问异常 ConcurrentModificationException

##### 3. CollectionsTest

```java
//aa= 0
//aa= 1
//aa= 2
//...      
//aa= 28
//aa= 29
//remove: 0
//remove: 1
//...
//remove: 8
//remove: 9
```

`Collections.synchronizedList(new ArrayList<>())` 得到 ArrayList 对象, 遍历时使用 synchronized (list) 加锁, 但是
remove() 时不加锁, 仍然不会发生 并发访问异常 ConcurrentModificationException

