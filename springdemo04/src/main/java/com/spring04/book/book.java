package com.spring04.book;

public class book {
    String bname,bauthor,pressname;
    double bprice;
    int bid;

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public book(String bname, String bauthor, String pressname, double bprice, int bid) {
        this.bname = bname;
        this.bauthor = bauthor;
        this.pressname = pressname;
        this.bprice = bprice;
        this.bid=bid;
    }
    public book() {
        this.bname = null;
        this.bauthor = null;
        this.pressname = null;
        this.bprice = 0;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public String getPressname() {
        return pressname;
    }

    public void setPressname(String pressname) {
        this.pressname = pressname;
    }

    public double getBprice() {
        return bprice;
    }

    public void setBprice(double bprice) {
        this.bprice = bprice;
    }
}
