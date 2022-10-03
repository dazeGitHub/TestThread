
##### SynBlockTest

将 Garden 的 `doSomething()` 外的 Log 先注释掉, 日志如下 :

```java
//INFO - --线程17 --- doSomething ---------- start
//INFO - --线程17 --- doSomething ---------- end
//INFO - --线程17 --- doSomething ---------- start
//INFO - --线程17 --- doSomething ---------- end
//INFO - --线程16 --- doSomething ---------- start
//INFO - --线程16 --- doSomething ---------- end
//INFO - --线程16 --- doSomething ---------- start
//INFO - --线程16 --- doSomething ---------- end
//INFO - --线程17 --- doSomething ---------- start
//INFO - --线程17 --- doSomething ---------- end
//...
```

