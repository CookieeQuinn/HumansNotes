package com.jnu.lxq;

public class PayRecord {
    private String Pay_Person;
    private String Pay_Date;
    private String Pay_Money;

    public PayRecord(String pay_Person, String pay_Date, String pay_Money) {
        Pay_Person = pay_Person;
        Pay_Date = pay_Date;
        Pay_Money = pay_Money;
    }

    public String getPay_Person() {
        return Pay_Person;
    }

    public String getPay_Date() {
        return Pay_Date;
    }

    public String getPay_Money() {
        return Pay_Money;
    }

    public void setPay_Person(String pay_Person) {
        Pay_Person = pay_Person;
    }

    public void setPay_Date(String pay_Date) {
        Pay_Date = pay_Date;
    }

    public void setPay_Money(String pay_Money) {
        Pay_Money = pay_Money;
    }




}
