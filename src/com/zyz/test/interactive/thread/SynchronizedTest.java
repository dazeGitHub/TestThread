package com.zyz.test.interactive.thread;

public class SynchronizedTest {

    public static void main(String[] args) {

        Vector vector = new Vector();
        new Thread(new Runnable() {
            @Override
            public void run() {
                vector.add(3);
            }
        }).start();
        vector.get();

//      ******** Vector get pre ********
//      ******** Vector get back ********
//      ------- Vector add pre -------
//      ------- Vector add back -------
    }
}

class Vector{

    //读和写应当区别对待, 但是 Vector 的读和写都是用的 synchronized
    public synchronized boolean add(Integer e) {
        System.out.println("------- Vector add pre -------");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("------- Vector add back -------");
        return true;
    }

    public synchronized void get() {
        System.out.println("******** Vector get pre ********");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("******** Vector get back ********");
    }
}