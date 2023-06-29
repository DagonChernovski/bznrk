package com.company;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class BillingClient {
    String localhost = "127.0.0.1";
    String RMI_HOSTNAME = "java.rmi.server.hostname";
    String SERVICE_PATH = "rmi://localhost/BillingService";
    public static String[][] CARDS = {
            {"Michael Ivanov", "1234567890"},
            {"Fedor Petrov",   "9876543210"},
            {"Feodor Sidorov", "8005553535"},
            {"Denis Testov",   "5820219213"}, //the King of this Humanity!
    };
    public double[] MONEYS = {135790.0, 24680.0, 23400.0, 9999999.0};
    public String[] PASSWORDS = {"MishaG0ld", "fedyaB05", "Mosyanya14", "God0fHumanity05"};
    BillingService bs;
    public BillingClient() {
        try {
            System.setProperty(RMI_HOSTNAME, localhost);
            // URL удаленного объекта
            String objectName = SERVICE_PATH;

            bs = (BillingService) Naming.lookup(objectName);

            System.out.println("\nRegister cards ...");
            registerCards(bs);
            System.out.println("Add moneys ...");
            addMoneys(bs);
            System.out.println("Get balance ...\n");
            getBalance(bs);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println("NotBoundException : " +
                    e.getMessage());
        }
    }

    private Card createCard(final int idx) {
        return new Card(CARDS[idx][0], CARDS[idx][1], 0, PASSWORDS[idx]);
    }
    private void registerCards(BillingService bs) {
        for (int i = 0; i < CARDS.length; i++) {
            try {
                Card card = createCard(i);
                bs.addNewCard(card);
            } catch (RemoteException e) {
                System.err.println("RemoteException : " +
                        e.getMessage());
            }
        }
    }

    private void addMoneys(BillingService bs)  {
        for (int i = 0; i < CARDS.length; i++) {
            try {
                Card card = bs.getCard(i);
                if (bs.getCardBalance(card) == 0)
                    bs.addMoney(card, MONEYS[i]);
            } catch (RemoteException e) {
                System.err.println("RemoteException : " +
                        e.getMessage());
            }
        }
    }
    private void getBalance(BillingService bs) {
        for (int i = 0; i < CARDS.length; i++) {
            try {
                Card card = bs.getCard(i);
                System.out.println("card : " +
                        card.getNumber() +
                        ", balance = " +
                        bs.getCardBalance(card));
            } catch (RemoteException e) {
                System.err.println("RemoteException : " +
                        e.getMessage());
            }
        }
    }

    private void moneyInterface(Scanner sc, Card receiver, Card source) {
        //если source=null, интерфейс работает как терминал - напрямую с деньгами
        short[] sumToAdd = new short[]{100, 200, 500, 1000, 2000, 5000};
        int int_input;
        int var = 0;
        boolean itWorks = true;
        while (itWorks) {
            System.out.flush();
            System.out.println("Введите сумму для зачисления:" +
                    "1 - 100\t2 - 200\t3 - 500\n4 - 1000\t5 - 2000\t6 - другая сумма\n" +
                    "7 - enter\t8 - выход\nТекущий счет: " + var);
            int_input = sc.nextInt() % 10;
            switch (int_input) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    if (source!=null)
                        if (var+sumToAdd[int_input - 1]>source.getMoney())
                        {System.out.println("Недостаточно денег"); break;}
                    var += sumToAdd[int_input - 1];
                    break;
                case 6:
                    System.out.println("Введите другую сумму");
                    int_input=sc.nextInt();
                    if (source!=null)
                        if (var+int_input>source.getMoney())
                        {System.out.println("Недостаточно денег"); break;}
                    var += int_input;
                    break;
                case 7:
                    System.out.println();
                    if (source == null) {
                        try {
                            bs.addMoney(receiver, var);
                            bs.log(receiver, " зачислено "+var+" рублей");
                        } catch (RemoteException e) {
                            e.printStackTrace(); //если препятствует зачислению денег, передает ошибку в банк
                        }
                        itWorks = false;
                        break;
                    } else {
                        if (source.getMoney() > var) {
                            try {
                                bs.subMoney(source, var);
                                bs.addMoney(receiver, var);
                                bs.log(source, var+" переведено на счёт "+receiver.getName());
                                bs.log(receiver, var+" начислено от "+source.getName());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                case 8:
                    itWorks = false;
                default:
                    break;
            }
        }
        return;
    }
    private Object safeScan(Scanner sc, Object result) {
        sc=new Scanner(System.in);
        if (result instanceof Integer) {
            result=sc.nextInt();
        }
        if (result instanceof String) {
            result=sc.nextLine();
        }
        return result;
    }

    private boolean almostEqual(String original, String checking) {
        //System.out.println(original.toLowerCase().trim()+"=?="+checking.toLowerCase().trim());
        if (original.toLowerCase().trim().equals(checking.toLowerCase().trim())) return true;
        else return false;
    }
    private boolean askControlQuestions(Scanner sc, Card card) {
        int rightInputs=0;
        String input;
        String[] controlQuestions = new String[3];
        sc=new Scanner(System.in);
        System.out.println("Девичья фамилия матери: ");
        controlQuestions[0] = sc.nextLine();
        System.out.println("Любимое животное");
        controlQuestions[1] = sc.nextLine();
        System.out.println("Любимая еда");
        controlQuestions[2] = sc.nextLine();
        for (int i=0; i<3; i++)
        if (almostEqual(controlQuestions[i],card.question(i))) {
            rightInputs++;
        }
        System.out.println("Верных догадок: "+rightInputs);
        Random rand=new Random();
        int saveCode=100+rand.nextInt(899);
        if (rightInputs>1) {
            System.out.println("Вход разрешен. Обратитесь по смене пин-кода к разработчику. Код: "+saveCode);
            try {
                bs.log(card, "Forgot password, emergency code "+saveCode);
            } catch (IOException e) {
                System.out.println("Технические неполадки, код недействителен");
                e.printStackTrace();
            }
            return true;
        }
        else {
            System.out.println("Попробуйте еще раз.");
            return false;
        }
    }
    private void workingInterface() throws RemoteException {
        String input;
        short clientID = -1;
        int int_input;
        boolean[] systemFlags = new boolean[]{true, false, false};
        /*
        Flag 0 - system is working.
        Flag 1 - user logged in
        Flag 2 - sub_menu is working
         */
        Scanner sc;
        while (systemFlags[0]) {
            while (clientID == -1) {
                System.out.println("Введите ваш номер карты");
                sc = new Scanner(System.in);
                input = sc.nextLine();
                try {
                    clientID = bs.findCard(input);
                } catch (RemoteException e) {
                    System.out.println("Не удается удаленно подключиться к клиенту");
                    e.printStackTrace();
                    continue;
                }
                if (clientID == -1) {
                    System.out.println("Карты не существует");
                    continue;
                } else break;
            }
            Card card = bs.getCard(clientID);
            if (card.blocked()) {
                System.out.println("Карта заблокирована.");
                continue;
            }
            for (short ii = 5; ii > 0 && systemFlags[1] == false; ii--) {
                System.out.println("Введите пин-код");
                sc = new Scanner(System.in);
                input = sc.nextLine();
                if (input == "00") {
                    if (card.hasControlQuestions()) {
                        systemFlags[1] = askControlQuestions(sc, card);
                        continue;
                    } else System.out.println("Контрольные вопросы недоступны.");
                }
                if (!card.confirmPIN(input)) {
                    System.out.println("Неверный PIN! Осталось попыток: " + ii);
                    if (ii < 4 && card.hasControlQuestions())
                        System.out.println("Подсказка: если забыли пароль, введите 00");
                } else systemFlags[1] = true;
            }
            if (systemFlags[1] == false) {
                System.out.println("Вход заблокирован. Время блокировки: 5 минут");
                bs.banCard(card);
                systemFlags[0] = false;
                break;
            }
            while (systemFlags[1]) {
                card = bs.getCard(clientID);
                System.out.println("[Пользователь: " + card.getName() + " Баланс " + card.getMoney() + ']');
                System.out.println(
                        "СИСТЕМА УПРАВЛЕНИЯ ЛИЧНЫМ СЧЕТОМ\n" +
                                "1 - зачисление денег\t2 - перевод на другой счет\n3 - оплата коммунальных услуг\t" +
                                "4 - оплата других услуг\n5 - просмотр журнала\t 6 - выход");
                sc = new Scanner(System.in);
                int_input = sc.nextInt();
                switch (int_input) {
                    case 1: {
                        systemFlags[2] = true;
                        moneyInterface(sc, card, null);
                        systemFlags[2] = false;
                        break;
                    }
                    case 2:
                        systemFlags[2] = true;
                        int receiverID;
                        Card receiver;
                        while (systemFlags[2]) {
                            System.out.flush();
                            System.out.println("Введите счет или имя, на который хотите перевести: (X - выход)");
                            sc = new Scanner(System.in);
                            input = sc.nextLine();
                            if (input.charAt(0) == 'X') {
                                systemFlags[2] = false;
                                break;
                            }
                            try {
                                receiverID = bs.findCard(input);
                                if (receiverID == -1) throw new Exception("wrong card ID!");
                                moneyInterface(sc, bs.getCard(receiverID), card);
                                systemFlags[2] = false;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:
                        System.out.println(
                                "Выберите услугу\n" +
                                        "1 - электроэнергия\t2 - горячая вода \n3 - холодная вода\t" +
                                        "4 - отопление\t 5 - газ\t 6 - выход");
                        sc = new Scanner(System.in);
                        input = sc.nextLine();
                        if (input.charAt(0) == '6') {
                            systemFlags[2] = false;
                            break;
                        }
                        break;
                    case 4:
                        System.out.println(
                                "Выберите услугу\n" +
                                        "1 - мобильная связь\t2 - Охрана и Домофон \t3 - Школы, Лицеи, дет. сады\n" +
                                        "4 - Институты и колледжи\t 5 - Интернет, Телефония, ТВ\t 6 - Налоги, штрафы, госпошлины\n" +
                                        "7 - Дополнительное образование\t 8 - Творчество и Кружки\t9 - Благотворительность и фонды\n" +
                                        "0 - Выход"
                        );
                        sc = new Scanner(System.in);
                        input = sc.nextLine();
                        if (input.charAt(0) == '0') {
                            systemFlags[2] = false;
                            break;
                        }
                        break;
                    case 5:
                        List<String> logs = bs.getAllLogs(card);
                        if (logs == null) System.out.println("Журнал пуст");
                        for (String s : logs)
                            System.out.println(s);
                        break;
                    case 6:
                        System.out.println("Изъятие карты...");
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        systemFlags[1] = false;
                        clientID = -1;
                        System.out.flush();
                        break;
                    case 7:
                        System.out.println("Чтобы войти в режим разработчика, введите имя разработчика");
                        sc = new Scanner(System.in);
                        //input=sc.nextLine();
                        if (sc.nextLine().equals("Denis Testov")) {
                            System.out.println("Режим разработчика включен. ");
                            System.out.println("1 - смена учетных данных, 2 - регистрация нового пользователя, 3 - выход");
                            sc = new Scanner(System.in);
                            int_input = sc.nextInt();
                            switch (int_input) {
                                case 1:
                                    boolean confirmation;
                                    if (!card.hasControlQuestions()) confirmation=true;
                                    else {
                                        System.out.println("Введите контрольные вопросы");
                                        confirmation = askControlQuestions(sc, card);
                                    }
                                    if (confirmation) {
                                        System.out.println("1 - изменить инициалы уч. записи, 2 - изменить пароль, 3 - выход");
                                        sc = new Scanner(System.in);
                                        if (sc.nextLine().equals("2")) {
                                            boolean passwordValid = false;
                                            do {
                                                System.out.println("Введите пароль: ");
                                                sc = new Scanner(System.in);
                                                input = sc.nextLine();
                                                if (input.matches(".*[a-z|а-я].*") && input.matches(".*[A-Z|А-Я].*") && input.matches(".*[0-9].*") && input.length() >= 8)
                                                    passwordValid = true;
                                                else System.out.println("Введите более точный пароль");
                                            } while (!passwordValid);
                                            card.setPassword(input);
                                            System.out.println("Пароль был изменен на " + input);
                                            bs.log(card,"Ваш пароль был изменен.");ы
                                        }
                                    }
                                    break;
                                case 2:
                                    System.out.println("Введите имя и фамилию клиента");
                                    sc=new Scanner(System.in);
                                    String name = sc.nextLine();
                                    System.out.println("Создание карты начато. Введите номер карты (10 цифр)");
                                    String number = sc.nextLine();
                                    if (number.length() == 10 && number.matches("[0-9]*")) {
                                        boolean passwordValid = false;
                                        do {
                                            System.out.println("Введите пароль: ");
                                            input = sc.nextLine();
                                            if (input.matches(".*[a-z].*") && input.matches(".*[A-Z].*") && input.matches(".*[0-9].*") && input.length() >= 8)
                                                passwordValid = true;
                                            else System.out.println("Введите более точный пароль");
                                        } while (!passwordValid);
                                        Card newCard = new Card(name, number, 0.0, input);
                                        String[] controlQuestions = new String[3];
                                        while (true) {
                                            System.out.println("Введите 3 контрольных вопроса. ");
                                            System.out.println("Девичья фамилия матери: ");
                                            sc = new Scanner(System.in);
                                            controlQuestions[0] = sc.nextLine();
                                            System.out.println("Любимое животное");
                                            controlQuestions[1] = sc.nextLine();
                                            System.out.println("Любимая еда");
                                            controlQuestions[2] = sc.nextLine();
                                            System.out.println("Итак, ваши данные: ");
                                            for (int i = 0; i < 3; i++) {
                                                System.out.println(controlQuestions[i]);
                                            }
                                            ;
                                            System.out.println("Подтвердить? (1 - да, 2 - нет)");
                                            int_input = sc.nextInt();
                                            if (int_input == 1) {
                                                newCard.assignControlQuestions(controlQuestions);
                                                try {
                                                    bs.addNewCard(newCard);
                                                } catch (RemoteException e) {
                                                    e.printStackTrace();
                                                    System.out.println("Failed to add card.");
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }

                        }
                        break;
                    default:
                        break;
                }
            }
        } //0 флажок снят = работа интерфейса прекращена
    }
    public static void main(String[] args)
    {
        BillingClient bc=new BillingClient();
        try {
            bc.workingInterface();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
