
##### GardenTest3

###### 1. 两个线程没有执行 garden.enterKiosk(name);

和之前为 openDoor() 和 enterRoom() 添加 synchronized 的效果相同 :

```java
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - b0 --线程 id = 17 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --enterRoom---------------end
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//
//INFO - b7 --线程 id = 17 --openDoor---start
//INFO - b7 --线程 id = 17 --openDoor---end
//INFO - b7 --线程 id = 17 --enterRoom---------------start
//INFO - b7 --线程 id = 17 --enterRoom---------------end
```

###### 2. 两个线程执行了 garden.enterKiosk(name);

一个线程进入 Synchronized 的同步方法, 会给当前对象加锁, 其他 Synchronized 会受到影响。
注意 : 对象加了锁不会对没有 Synchronized 的方法产生影响, 

```java
//INFO - a0 --线程 id = 16 --openDoor---start
//INFO - a0 --线程 id = 16 --openDoor---end
//INFO - b0 --线程 id = 17 --openDoor---start
//INFO - a0 --线程 id = 16 --enterKiosk ************** start
//INFO - b0 --线程 id = 17 --openDoor---end
//INFO - b0 --线程 id = 17 --enterKiosk ************** start
//INFO - a0 --线程 id = 16 --enterKiosk ************** end
//INFO - a0 --线程 id = 16 --enterRoom---------------start
//INFO - b0 --线程 id = 17 --enterKiosk ************** end
//INFO - a0 --线程 id = 16 --enterRoom---------------end
//...
//INFO - b4 --线程 id = 17 --enterKiosk ************** start
//INFO - a3 --线程 id = 16 --enterRoom---------------start
//INFO - b4 --线程 id = 17 --enterKiosk ************** end
//INFO - a3 --线程 id = 16 --enterRoom---------------end
```

