
#### SynStaticTest

```java
//INFO - m2 ************* start
//INFO - m2 ************* end
//INFO - m1 ... start
//INFO - m1 ... end
//INFO - m2 ************* start
//INFO - m2 ************* end
//INFO - m1 ... start
//INFO - m1 ... end
//INFO - m1 ... start
//INFO - m1 ... end
```

m1() 和 m2() 是顺序执行, 而不是并发执行的, 一个线程访问 m1() 和 m2() 时, 其他线程都需要排队, 
所以用 synchronized 修饰 m1() 和 m2() 是有效的
