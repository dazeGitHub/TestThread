
##### GardenTest

###### 1. 不使用锁

```java
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//...
//INFO - a6 --线程 id = 16 --openDoor---end
//INFO - a6 --线程 id = 16 --enterRoom---------------start
//...
```

在 b0 进行 openDoor start 的时候还没 openDoor end, 但 a0 就挤进来了, 这是不合适的

###### 2. 使用 Synchronized 给方法加锁

期望的结果 :

1. 一个线程 openDoor 时其他线程可以 enterRoom, 但不可以 openDoor
2. 一个线程 enterRoom 时其他线程可以 openDoor, 但不可以 enterRoom

即 openDoor 和 enterRoom 之间是互不影响的

```java
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --enterRoom---------------end
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//...
//INFO - b3 --线程 id = 17 --openDoor---start
//INFO - b3 --线程 id = 17 --openDoor---end
//INFO - b3 --线程 id = 17 --enterRoom---------------start
//INFO - b3 --线程 id = 17 --enterRoom---------------end
```

线程 16 在 openDoor 时没有被 线程 17 抢占。
线程 17 在 openDoor 时也不会被线程 16 抢占。
但是 openDoor 之间也不能 enterRoom , 在 enterRoom 之间也不能 openDoor, 两个方法互相影响了, 与期望的结果不符
