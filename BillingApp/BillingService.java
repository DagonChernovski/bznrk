package com.company;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BillingService extends Remote {

    // Регистрация новой карты
    public void addNewCard(Card card) throws RemoteException;
    public Card getCard(final int idx) throws RemoteException;
    public short findCard(String num) throws RemoteException;
    // Добавление денежных средств на карту
    public void addMoney(Card card, double money)
            throws RemoteException;
    public void log(Card card, String loggedText) throws RemoteException;
    public List<String> getAllLogs(Card card) throws RemoteException;
    // Снятие денежных средств с карты
    public void subMoney(Card card, double money)
            throws RemoteException;
    public void banCard(Card card) throws RemoteException;
    // Получение баланса карты
    public double getCardBalance(Card card)
            throws RemoteException;
}
