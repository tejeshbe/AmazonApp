package com.example.amazonapp.model;

public class AdminOrders {
    String name,phone,city,state,add,date,time,totalAmont;

    public AdminOrders() {
    }

    public AdminOrders(String name, String phone, String city, String state, String add, String date, String time, String totalAmont) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.add = add;
        this.date = date;
        this.time = time;
        this.totalAmont = totalAmont;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmont() {
        return totalAmont;
    }

    public void setTotalAmont(String totalAmont) {
        this.totalAmont = totalAmont;
    }
}
