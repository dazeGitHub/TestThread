package com.zyz.test.family.bean;

public class Family {
    private Father father;
    private Wife wife;
    private Son son;
    private double account; //公用账户

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    public Son getSon() {
        return son;
    }

    public void setSon(Son son) {
        this.son = son;
    }

    public double getAccount() { //synchronized
        return account;
    }

    public void setAccount(double account) { //synchronized
        this.account = account;
    }
}
