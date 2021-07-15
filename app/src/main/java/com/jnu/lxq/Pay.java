package com.jnu.lxq;

public class Pay {

    private String name;
    private String money;
    private String date;

    public Pay(String name, String money, String date) {
        this.name = name;
        this.money = money;
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public String getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
