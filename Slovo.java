package com.company;

import java.io.*;
//import java.util.Arrays;
import java.util.*;

import static com.company.position.*;

enum position {left, center, right};
public class Main {
    static void ReadFrom(File f, List<String> w) {
        try {
            FileReader fr = new FileReader(f);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                w.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean MaskCheck(String mask, String w) {
        boolean thiz = true;
        if (mask.length()>w.length()) return false;
        for (int j = 0; j < mask.length(); j++) {
            if (mask.charAt(j) == '_') continue;
            if (mask.charAt(j) != w.charAt(j)) {
                thiz = false;
                break;
            }
        }
        return thiz;
    }
    public static List<String> FindWord(String mask, List<String> w, position pos, int _len) {
        boolean thiz = false;
        int len = _len;
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < w.size(); i++) {
            if (_len==-1)
                len=w.get(i).length();
            if (len == w.get(i).length() && mask.length()<len) {
                switch (pos) {
                    case left:
                        thiz = MaskCheck(mask, w.get(i));//.substring(0, len-1));
                        break;
                    case right:
                        thiz = MaskCheck(mask, w.get(i).substring(w.get(i).length() - mask.length()));
                        break;
                    case center:
                        for (int j = 0; j <= len - mask.length(); j++) {
                            System.out.println(w.get(i).substring(j, j + mask.length()));
                            thiz = MaskCheck(mask, w.get(i).substring(j, j + mask.length()));
                            if (thiz) break;
                        }
                        break;
                }
            }
            if (thiz) {
                ret.add(w.get(i));
                thiz = false;
            }
        }
        return ret;
    }
    public static int locateWord(String word, List<String> list) {
        if (!list.contains(word)) return -1;
        int left=0, right=list.size()-1;
        int pivot=(left+right)/2;
        while (!list.get(pivot).equals(word)) {
            switch(C2S(list.get(pivot),word)) {
                case '<':
                    left=pivot;
                    pivot=(left+right)/2;
                    break;
                case '>':
                    right=pivot;
                    pivot=(left+right)/2;
                    break;
                case '=':
                    break;
            }
        }
        return pivot;
    }
    public static void addWord(String word, List<String> list) { //добавление слова в список с последующей сортировкой
        if (list.contains(word)) return;
        int left=0, right=list.size()-1;
        int pivot=(left+right)/2;
        do {
            switch (C2S(list.get(pivot), word)) {
                case '<':
                    left = pivot;
                    pivot = (left + right) / 2;
                    break;
                case '>':
                    right = pivot;
                    pivot = (left + right) / 2;
                    break;
                case '=':
                    break;
            }
            if (pivot==0 || pivot>=list.size()-2) break;
        } while ((C2S(list.get(pivot), word) == '<' && pivot!=0) || C2S(list.get(pivot-1), word) == '>');
        list.add(pivot,word);
    }

    public static char C2S(String a, String b) { //посимвольное сравнение строк
        int aa = a.length();
        int bb = b.length();
        for (int i = 0; i < (aa < bb ? aa : bb); i++) {
            if (a.charAt(i) < b.charAt(i)) return '<';
            if (a.charAt(i) > b.charAt(i)) return '>';
        }
        if (aa > bb) return '>';
        if (aa < bb) return '<';
        return '=';
    }

    public static void SaveTo(List<String> words, File f) { //сохранить в файл по директории
        try (FileWriter writer=new FileWriter(f, true)) {
            for (String s: words) {writer.write(s); writer.append('\n');}
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        boolean extendedSearch =true;
        String filename = "C:\\Users\\denpo\\IdeaProjects\\WerdFinder\\russian_nouns.txt";
        File f = new File(filename);
        //System.out.println("Reading from: "+filename);
        ReadFrom(f, words);
        System.out.println("Добро пожаловать в систему \"поиск слов\"!\n" +
                " На данный момент в базе хранится " + words.size() + " слов.\n" +
                "Для получения информации об использовании информационной системы введите /помощь.");
        Scanner sc = new Scanner(System.in);
        do {
            String input = sc.nextLine();
            if (input.length()<1) continue;
            if (input.charAt(0) != '/') {
                System.out.println("Введите /поиск для поиска по маске");
                continue;
            }
            if (input.equals("/инфо")) {
                System.out.println("Всего хранится " + words.size() + " слов.");
            }
            if (input.equals("/помощь")) {
                System.out.println("Список команд:\n " +
                        "/помощь - собственно список команд\n" +
                        "/сохранить - сохранить текущую базу\n" +
                        "/сохркак - сохранить словарь в указанный файл\n" +
                        "/маска или /поиск - поиск слова по заданной маске через буквы и '_'\n" +
                        "/меню - выход из поиска по маске\n" +
                        "/добавить - добавление слова\n" +
                        "/удалить - удаление слова\n" +
                        "/стоп - остановить программу");
            }
            if (input.equals("/настройки")) {
                boolean exit=false;
                do {
                    System.out.println("Тип поиска:\n0 - классический\n1 - по маске\n/выход - выйти");
                    switch (sc.nextLine()) {
                        case "0":
                            extendedSearch = false;
                            break;
                        case "1":
                            extendedSearch = true;
                            break;
                        case "/выход":
                            exit=true;
                            break;
                        default:
                            break;
                    }
                } while (!exit);
            }
            position pos=center; // по умолчанию
            int len=0;
            if (input.equals("/поиск")) {
                do {
                    System.out.println("Введите маску слова");
                    do input = sc.nextLine(); while (input==null);
                    if (input.equals("/меню") || input.equals("/стоп")) break;
                    if (extendedSearch ==true) {
                        System.out.println("Введите длину слова (для любой длины можно ввести \"_\")");
                        char _len = sc.nextLine().charAt(0);
                        if (_len=='_') len=-1;
                        else {
                            if (_len<'0' && _len>'9') {
                                System.out.println("Ошибка ввода: поиск прерван");
                                break;
                            }
                            len = Integer.parseInt(String.valueOf(_len));
                            if (len < input.length()) {
                                System.out.println("Ошибка данных");
                                break;
                            }
                        }
                        System.out.println("Введите позицию маски (left, center, right)");
                        switch (sc.nextLine())
                        {
                            case "left":
                                pos=left;
                                break;
                            case "right":
                                pos=right;
                                break;
                            case "center":
                                pos=center;
                                break;
                        }
                    }
                    else len=input.length();
                    System.out.println("Подождите, пожалуйста...");
                    List<String> found = FindWord(input, words, pos, len);
                    System.out.println("Найдено слов: " + found.size());
                    for (String s : found) {
                        System.out.println(s);
                    }
                } while (true);
            }
            if (input.equals("/добавить")) {
                System.out.println("Введите слово для добавления");
                String word=sc.nextLine();
                if (words.contains(word)) {System.out.println("Такое слово уже существует"); continue;}
                System.out.println("Добавить слово \""+word+"\" в базу? (да,нет)");
                input=sc.nextLine();
                switch (input.toLowerCase(Locale.ROOT)) {
                    case "да": case "/да":
                        addWord(word,words);
                        System.out.println("Добавлено. Индекс: "+locateWord(word,words));
                        break;
                    default: break;
                }
            }
            if (input.equals("/удалить")) {
                System.out.println("Введите слово для удаления");
                String word=sc.nextLine();
                System.out.println("Удалить слово\""+word+"\"? (да,нет)");
                input=sc.nextLine();
                switch (input.toLowerCase(Locale.ROOT)) {
                    case "да": case "/да":
                        words.remove(word);
                        System.out.println("Удалено.");
                        break;
                    default: break;
                }
            }
            if (input.equals("/сохранить")) {
                System.out.println("Сохранить файл? (да, нет)");
                SaveTo(words, f);
            }
            if (input.equals("/сохркак")) {
                System.out.println("Введите название файла (директория C:\\Users\\denpo\\IdeaProjects\\WerdFinder\\");
                String fname2="C:\\Users\\denpo\\IdeaProjects\\WerdFinder\\"+sc.nextLine();
                File f2=new File(fname2);
                SaveTo(words,f2);
            }
            if (input.equals("/стоп")) break;
        } while (true);
    }
}
