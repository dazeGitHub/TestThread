
##### 1. Garden3_1

###### 使用 ReentrantLock 局部变量

第一个线程调用 openDoor() 时线程栈会产生一个 lock, 第二个线程调用 openDoor() 时线程栈又会产生一个新的 lock
第一个线程的 openDoor() 和 第二个线程的 openDoor() 使用的不是一个 lock 对象, 所以根本无法保证两个线程不会同时进入 openDoor(),

```java
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//INFO - a1 --线程 id = 16 --openDoor---start
//INFO - b0 --线程 id = 17 --enterRoom---------------end
//...
//INFO - b7 --线程 id = 17 --enterRoom---------------start
//INFO - a7 --线程 id = 16 --enterRoom---------------end
//INFO - a8 --线程 id = 16 --openDoor---start
//INFO - b7 --线程 id = 17 --enterRoom---------------end
```

可见 使用局部变量的 ReentrantLock 无法达到预期效果。

##### 2. Garden3_2

###### 使用一个 ReentrantLock 全局变量

```java
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --enterRoom---------------end
//...
//INFO - b8 --线程 id = 17 --openDoor---start
//INFO - b8 --线程 id = 17 --openDoor---end
//INFO - b8 --线程 id = 17 --enterRoom---------------start
//INFO - b8 --线程 id = 17 --enterRoom---------------end
```

效果与使用 synchronized 为 openDoor() 和 enterRoom() 方法 加锁相同

###### 3. Garden3_3

###### 使用两个 ReentrantLock 全局变量

```java
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//INFO - a1 --线程 id = 16 --openDoor---start
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - a1 --线程 id = 16 --openDoor---end
//...
//INFO - a14 --线程 id = 16 --enterRoom---------------start
//INFO - b14 --线程 id = 17 --openDoor---start
//INFO - a14 --线程 id = 16 --enterRoom---------------end
//INFO - b14 --线程 id = 17 --openDoor---end
```

