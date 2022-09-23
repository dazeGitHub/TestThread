package com.zyz.test.interactive.gather;

public class Book {
    private String bname;
    private double price;

    public Book(String bname) {
        this.bname = bname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
