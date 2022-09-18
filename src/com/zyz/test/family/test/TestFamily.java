package com.zyz.test.family.test;

import com.zyz.test.family.bean.Family;
import com.zyz.test.family.bean.Father;
import com.zyz.test.family.bean.Son;
import com.zyz.test.family.bean.Wife;

public class TestFamily {
    public static void main(String[] args) {
        Family family = new Family();
        Father father = new Father("father1", family);
        Wife wife = new Wife("wife1", family);
        Son son = new Son("son1", family);

        new Thread(new FatherThread(father)).start();
        new Thread(new WifeThread(wife)).start();
        new Thread(new SonThread(son)).start();
    }
}

class FatherThread implements Runnable{
    private Father father;

    public FatherThread(Father father){
        this.father = father;
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(300);      //让钱越来越不够花
                this.father.earnMoney(10);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class WifeThread implements Runnable{
    private Wife wife;

    public WifeThread(Wife wife){
        this.wife = wife;
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(200);
                this.wife.shopping(7);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class SonThread implements Runnable{
    private Son son;

    public SonThread(Son son){
        this.son = son;
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(200);
                son.playGame(5);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}