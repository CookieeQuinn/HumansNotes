package com.jnu.lxq.dataprocessor;

import android.content.Context;

import androidx.annotation.Nullable;

import java.io.Serializable;

import static java.sql.Types.NULL;

public class Receive implements Serializable {
    private Context Context;
    private String name;
    private String money;
    private String date;
    private String reason;
    private int reason_position;

    public String getName() {
        return name;
    }
    public String getMoney() {
        return money;
    }
    public String getDate() {
        return date;
    }
    public String getReason() { return reason; }
    public int getReason_position() { return reason_position; }

    public void setName(String name) {
        this.name = name;
    }
    public void setMoney(String money) {
        this.money = money;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setReason(String reason) { this.reason =reason; }
    public void setReason_position(int reason_position) { this.reason_position = reason_position; }

    public Receive(Context context,String name, String money, String date,int reason_position) {
        this.name = name;
        this.money = money;
        this.date = date;
        this.reason_position = reason_position;

        ReasonDataBank reasonDataBank = new ReasonDataBank(context);
        reasonDataBank.Load();
        this.reason = reasonDataBank.getReasonList().get(reason_position);
    }
}
