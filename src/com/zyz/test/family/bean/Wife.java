package com.zyz.test.family.bean;

public class Wife {
    private String name;
    private final Family family;

    public Wife(String name, Family family) {
        this.name = name;
        this.family = family;
        this.family.setWife(this);
    }

    public String getName() {
        return name;
    }

    public Family getFamily() {
        return family;
    }

    //这里就牵扯到多线程的消费了
    public void shopping(double money){
        synchronized (this.family){
            double leftMoney = this.family.getAccount(); //先查看剩余金额
            System.out.println("Wife 消费前: family account 剩余" + leftMoney);
            //在查询和消费之间延时, 那么多线程时更容易发生错误, 出现异常数据
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(money > leftMoney){
                System.out.println("账户金额不足, 请 wife 暂停消费 ------");
            }else{
                //leftMoney = leftMoney - money;
                leftMoney = this.family.getAccount() - money;
                this.family.setAccount(leftMoney); //回写金额
                System.out.println("Wife 消费后 : family account 剩余" + leftMoney);
            }
        }
    }
}
