
##### 1. 未使用锁前

可见由于 公用账户剩余金额 account 不同步, 导致数据发生错乱。

Father 在充值前会判断 leftMoney, 如果剩余金额 leftMoney < 0, 那么打印警告 "???" 提示余额不足

日志如下 :

```java
//Son 消费前: family account 剩余10.0
//Wife 消费后 : family account 剩余3.0
//Son 消费后 : family account 剩余-2.0
//Wife 消费前: family account 剩余-2.0
//Son 消费前: family account 剩余-2.0
//账户金额不足, 请 son 暂停消费 ------
//账户金额不足, 请 wife 暂停消费 ------
//警告: Father earnMoney 充值前 leftMoney = -2.0????????????????
//Father 充值前: family account 剩余-2.0
//Father 充值后: family account 剩余 8.0
```

日志可见金额竟然出现了负数

##### 2. 锁 account 的 get/set 方法 - 未解决

为 `getAccount()` 和 `setAccount()` 添加锁 :

```java
public class Family {
    private double account; //公用账户
    
    public synchronized double getAccount() {
        return account;
    }

    public synchronized void setAccount(double account) {
        this.account = account;
    }
}
```

日志如下 :

```java
//Son 消费前: family account 剩余10.0
//Son 消费后 : family account 剩余5.0
//Wife 消费后 : family account 剩余-2.0
//警告: Father earnMoney 充值前 leftMoney = -2.0????????????????
//Father 充值前: family account 剩余-2.0
//Father 充值后: family account 剩余 8.0
```

日志可见金额仍然出现了负数

##### 3. 

