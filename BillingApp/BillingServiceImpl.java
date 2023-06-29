package com.company;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BillingServiceImpl extends UnicastRemoteObject
        implements BillingService {
    private static final long serialVersionUID = 1L;
    private static File mainLog=new File("log2.txt");
    private static List<Card> cards;
    private static List<String> logs;
    FileWriter writer=new FileWriter(mainLog);
    FileReader fr=new FileReader(mainLog);
    // инициализация сервера
    public BillingServiceImpl() throws IOException {
        super();
        cards = new ArrayList<Card>();
    }
    public short findCard(String num) throws RemoteException {
        short CardID = -1;
        for (short i = 0; i < cards.size(); i++)
            if (cards.get(i).getNumber().equals(num)) {
                CardID = i;
                break;
            }
        return CardID;
    }
    @Override
    public void addNewCard(Card card) throws RemoteException {
        if (findCard(card.getNumber())!=-1) return;
        cards.add(card);
        String log="register card : " + card.getNumber() + ", id :: "+cards.size();
        System.out.println(log);
        log(card, "Владелец счета "+card.getName()+" зарегистрирован на сервере");
    }
    public Card getCard(final int idx) throws RemoteException {
        Card toBeCreated = cards.get(idx);
        return toBeCreated;
    }
    @Override
    public void addMoney(Card card, double money)
            throws RemoteException {
        for (Card crd : cards) {
            if (crd.equals(card)) {
                crd.setMoney(crd.getMoney() + money);
                System.out.println("add money : "
                        + "card " + card.getNumber()
                        + ", summa = " + money);
                break;
            }
        }
    }

    @Override
    public void subMoney(Card card, double money)
            throws RemoteException {
        for (Card crd : cards) {
            if (crd.equals(card)) {
                crd.setMoney(crd.getMoney() - money);
                String output="sub money : "
                        + "card : " + card.getNumber()
                        + ", summa = " + money;
                System.out.println(output);
                break;
            }
        }
    }
    @Override
    public double getCardBalance(Card card)
            throws RemoteException {
        double balance = 0;
        for (Card crd : cards) {
            if (crd.equals(card)) {
                balance = crd.getMoney();
                String output="balance : "
                        + "card : " + card.getNumber()
                        + ", summa = " + balance;
                System.out.println(output);
                break;
            }
        }
        return balance;
    }
    public void banCard(Card card) throws RemoteException {
        card.blockCard();
        log(card, "access denied");
        try {
            TimeUnit.SECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
            card.unblockCard();
            log(card, "access granted");
        }
    }
    public void log(Card source, String loggedText) {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime now=LocalDateTime.now();
        String temp=source.getNumber() + " :: " + loggedText + ", время :: " + dtf.format(now);
        System.out.println(temp);
        logs.add(temp);
    }
    public List<String> getAllLogs(Card card) throws RemoteException {
        List<String> allLogs = new ArrayList<String>();
        int i = 0;
        for (String line : logs) {
            if (line.substring(0, 10).equals(card.getNumber())) {
                allLogs.add(line);
            }
        }
        return allLogs;
    }
    /*
    Структура лога:
    операция - add money, balance, type
    while ((c = fr.read(buf)) > 0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
            } allLogs[i]=String.valueOf(buf); i++;
     */
    /**
     * Старт удаленного RMI объекта BillingService
     *
     * @param args аргументы
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String localhost = "127.0.0.1";
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        logs=new ArrayList<String>();
        try {
            System.setProperty(RMI_HOSTNAME, localhost);
            // Создание удаленного RMI объекта
            BillingService service = new BillingServiceImpl();

            // Определение имени удаленного RMI объекта
            String serviceName = "BillingService";
            System.out.println("Initializing " + serviceName);
            /**
             * Регистрация удаленного RMI объекта BillingService
             * в реестре rmiregistry
             */
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serviceName, service);
            System.out.println("Start " + serviceName);
            System.out.println();
//            System.out.println("Enter card name");
//            Scanner sc = new Scanner(System.in);
//            String cardName=sc.nextLine();
//            Card card1=new Card(cardName, "34003424", 0D);
//            service.addNewCard(card1);
//            service.addMoney(cards.get(0), 1200);
//            service.addMoney(card1, 1600);
//            service.getCardBalance(card1);
        } catch (RemoteException e) {
            System.err.println("RemoteException : " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Exception : " + e.getMessage());
            System.exit(2);
        }
    }
}
