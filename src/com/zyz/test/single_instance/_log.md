
##### 1. 懒汉模式 (不加锁)

##### 单线程模式

```java
// instance...
```

##### 多线程模式 (不加锁)

```java
//instance...,  Thread id = 16
//instance...,  Thread id = 20
//instance...,  Thread id = 19
//instance...,  Thread id = 22
//instance...,  Thread id = 25
//instance...,  Thread id = 21
//instance...,  Thread id = 24
//instance...,  Thread id = 17
//instance...,  Thread id = 23
//instance...,  Thread id = 18
```

多线程不加锁调用了很多次构造器, 白白创建了很多 Singleton 对象。

##### 2. 饿汉模式

```java
// SingletonHunger,  Thread id = 16
```

##### 3. 方法直接加锁的 懒汉模式

```java
//instance...,  Thread id = 19
```

##### 4. 锁代码块的 懒汉模式

```java
//instance...,  Thread id = 16
```

##### 5. 使用双重校验锁的 懒汉模式

```java
//instance...,  Thread id = 19
```