
##### SynStaticTest2

###### 1. 使用类调用 m1() 和 m2() 静态方法, 使用对象调用 m3() 和 m4() 成员方法

```java
//INFO - m1 ... start
//INFO - m1 ... end
//INFO - m3 ++++++++ start
//INFO - m1 ... start
//INFO - m1 ... end
//INFO - m3 ++++++++ end
//INFO - m2 ************* start
//INFO - m3 ++++++++ start
//INFO - m2 ************* end
//INFO - m3 ++++++++ end
```

可见 m3 start 和 m3 end 之间有 m1, m2 start 和 m2 end 之间也有 m3

* m1() 和 m2() 是互斥的, 锁的是 Class 对象;
* m3() 和 m4() 是互斥的, 锁的是 当前对象;

但是它们之间不是互斥的, 即 m1()、m2()和 m3()、m4() 不是互斥的。

###### 2. 使用类调用 m1() 和 m2() 静态方法, 使用对象调用 内部锁了 Class 对象的 m5() 和 m6() 成员方法

```java
//INFO - m2 ************* start
//INFO - m2 ************* end
//INFO - m6 %%%%%%%%% start
//INFO - m6 %%%%%%%%% end
//INFO - m1 ... start
//INFO - m1 ... end
//INFO - m1 ... start
//INFO - m1 ... end
//INFO - m5 @@@@@@ start
//INFO - m5 @@@@@@ end
```

日志可见, m1()、m2()、m5()、m6() 之间是互斥的
