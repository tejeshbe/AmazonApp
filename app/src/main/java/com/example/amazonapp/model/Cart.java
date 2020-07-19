package com.example.amazonapp.model;

public class Cart {
    String pprice,pname,quantity,pid;

    public Cart() {
    }

    public Cart(String pprice, String pname, String quantity, String pid) {
        this.pprice = pprice;
        this.pname = pname;
        this.quantity = quantity;
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
