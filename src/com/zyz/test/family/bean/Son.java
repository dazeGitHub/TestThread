package com.zyz.test.family.bean;

public class Son {
    private String name;
    private final Family family;

    public Son(String name, Family family) {
        this.name = name;
        this.family = family;
        this.family.setSon(this);
    }

    public String getName() {
        return name;
    }

    public Family getFamily() {
        return family;
    }

    public void playGame(double money){
        synchronized (this.family){
            double leftMoney = this.family.getAccount(); //先查看剩余金额
            System.out.println("Son 消费前: family account 剩余" + leftMoney);
            if(money > leftMoney){
                System.out.println("账户金额不足, 请 son 暂停消费 ------");
            }else{
                //leftMoney = leftMoney - money;
                leftMoney = this.family.getAccount() - money;
                this.family.setAccount(leftMoney); //回写金额
                System.out.println("Son 消费后 : family account 剩余" + leftMoney);
            }
        }
    }
}
