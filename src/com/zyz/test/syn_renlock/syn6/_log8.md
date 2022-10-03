
#### SynAchieveReLockTest

##### LogWidget 对象的 doSomething() 同步方法调用了 super.doSomething(); 

日志如下 :

```java
//son is do something..., thread id = 16
//widget is do something..., thread id = 16
//son is do something..., thread id = 25
//widget is do something..., thread id = 25
//son is do something..., thread id = 24
//widget is do something..., thread id = 24
```

可见并没有出现死锁现象, 而是重入现象。

###### 在 LogWidget 的 doSomething() 里再调用 doOthers() 

```java
//son is do something..., thread id = 16
//widget is do something..., thread id = 16
//son is do Others..., thread id = 16
//son is do something..., thread id = 25
//widget is do something..., thread id = 25
//son is do Others..., thread id = 25
//son is do something..., thread id = 24
//widget is do something..., thread id = 24
//son is do Others..., thread id = 24
```

仍然没有出现死锁现象, 而是重入现象。