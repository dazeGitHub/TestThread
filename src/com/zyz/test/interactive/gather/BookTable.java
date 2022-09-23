package com.zyz.test.interactive.gather;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 使用 BookTable 模拟数据库, 将其当做图书的一个表
 */
public class BookTable {
    private List<Book> bkTable;

    //使用 Vector 和 ArrayList 做测试, Vector 和 ArrayList 都是 List 的实现类
    public BookTable(){
        //bkTable = new Vector<>(30);
        bkTable = new ArrayList<>();
    }

    //这里的操作方法都没有使用 synchronized, 但是 Vector 的底层方法实现都是使用了 synchronized
    public void add(Book bk){
        bkTable.add(bk);
    }

    public Book get(int index) {
        return bkTable.get(index);
    }

    public int size(){
        return bkTable.size();
    }
}
