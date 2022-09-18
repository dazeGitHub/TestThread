package com.zyz.test.tickets.bean;

import java.util.Date;

public class Ticket {
    private String seatno;      //座位号
    private Date startTime;
    private String startSite;   //站点

    public Ticket(String seatno){
        this.seatno = seatno;
    }

    public String getSeatno() {
        return seatno;
    }

    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartSite() {
        return startSite;
    }

    public void setStartSite(String startSite) {
        this.startSite = startSite;
    }
}
