package com.company;

import java.io.Serializable;
import java.rmi.*;

public class Card implements Serializable
{
    private static final long serialVersionUID = 1L;

    private  String  name;
    private  String  number;
    private String password;
    private  double  money;
    private String[] controlQuestions;
    private boolean isBlocked=false;
    private boolean init; //инициализирована карта или нет. Деньги хранятся в файле, принадлежащем серверу.
    public Card(String name, String number, double money,String password)
    {
        super();
        this.name   = name;
        this.number = number;
        this.money  = money;
        this.password = password;
    }
    @Override
    public boolean equals(Object card)
    {
        Card crd = (Card)card;
        return this.getNumber().equals(crd.getNumber());
    }
    protected void blockCard() {
        isBlocked = true;
    }
    protected void unblockCard() {
        isBlocked=false;
    }
    protected boolean blocked() {return isBlocked;};
    protected void initializeCard() {
        init=true;
    }
    void setMoney(double money)
    { this.money=money;}
    public void assignControlQuestions(String[] questions) {
        controlQuestions=questions;
    }
    public boolean hasControlQuestions() {
        return !(controlQuestions==null);
    }
    public String question(int i)
    {
        return controlQuestions[i];
    }
    String getName() {return name;}
    double getMoney() {return money;}
    String getNumber() {return number;}
    void setPassword(String newPassword) {
        password=newPassword;
    };
    boolean confirmPIN(String input) {
        return (password.equals(input));
    }
}

