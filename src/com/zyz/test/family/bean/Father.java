package com.zyz.test.family.bean;

public class Father {
    private String name;
    private final Family family;

    public Father(String name, Family family) {
        this.name = name;
        this.family = family;
        this.family.setFather(this);
    }

    public String getName() {
        return name;
    }

    public Family getFamily() {
        return family;
    }

    //挣钱
    public void earnMoney(double money){
        synchronized (this.family){
            double leftMoney = this.family.getAccount(); //先查看剩余金额
            if(leftMoney < 0){
                System.out.println("警告: Father earnMoney 充值前 leftMoney = " + leftMoney + "????????????????");
            }
            System.out.println("Father 充值前: family account 剩余" + leftMoney);
            leftMoney += money;
            this.family.setAccount(leftMoney); //回写金额
            System.out.println("Father 充值后: family account 剩余 " + leftMoney);
        }
    }
}
