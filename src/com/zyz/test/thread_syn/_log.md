
##### VolatileTest

```java
//val is 103 not even!
//val is 113 not even!
//val is 109 not even!
//val is 105 not even!
//val is 117 not even!
//val is 115 not even!
//val is 111 not even!
//val is 99 not even!
//val is 107 not even!
//val is 101 not even!
```

val 出现奇数的原因, EvenGenerator 的 next() 中的 ++currentEvenValue 是非原子操作。

###### currentEvenValue 前面加 volatile 的原因

编译器如果发现这里是相同的指令, 有可能出现优化, 即两条相同的代码可能会被优化成一句, 所以要为 currentEvenValue 前面加 volatile

```java
class EvenGenerator extends IntGenerator {

    private volatile int currentEvenValue = 0;

    public int next() {
        ++currentEvenValue;                                // Danger point here!
        ++currentEvenValue;
        return currentEvenValue;
    }
}
```

解决方案 : 使用 synchronized 为 next() 方法加锁, 运行后因为 val 一直是偶数, 所以程序不会终止

```java
class EvenGenerator extends IntGenerator {

    private volatile int currentEvenValue = 0;

    public synchronized int next() {
        ++currentEvenValue;                                // Danger point here!
        ++currentEvenValue;
        return currentEvenValue;
    }
}
```
