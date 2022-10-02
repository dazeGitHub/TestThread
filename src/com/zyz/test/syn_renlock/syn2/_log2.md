
##### GardenTest2

###### 使用 synchronized 为不同的 object 对象加锁

```java
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//INFO - a1 --线程 id = 16 --openDoor---start
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --enterRoom---------------end
//...
//INFO - b9 --线程 id = 17 --openDoor---start
//INFO - a9 --线程 id = 16 --enterRoom---------------end
//INFO - b9 --线程 id = 17 --openDoor---end
//INFO - b9 --线程 id = 17 --enterRoom---------------start
```

可见 线程16 和 线程17 openDoor 和 enterRoom 之间是互不影响的, 并且 openDoor 时别的线程无法 openDoor,
enterRoom 时别的线程也无法 enterRoom, 即 使用 synchronized 为不同的 object 对象加锁 与 期望的结果 相符。
