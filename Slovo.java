package com.company;

import java.io.*;
//import java.util.Arrays;
import java.util.*;

//import static com.company.speech.*;


//enum speech {unknown, noun, adj, verb, interj, adverb};

public class Main {
    /*public static class Slovo {
        String word;
        speech part;
        Slovo (String word, speech part)
        {
            this.word=word;
            this.part=part;
        }
    }*/
    static void ReadFrom(File f, List<String> w) {
        try {
            FileReader fr = new FileReader(f);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                w.add(line);
                line = reader.readLine();
            }
            //System.out.print(Arrays.toString(w.lastElement().word) +" ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> FindWord(String mask, List<String> w) {
        //if (!mask.contains("%"))
        boolean thiz = false;
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < w.size(); i++) {
            if (mask.length() == w.get(i).length()) {
                thiz = true;
                //System.out.print(w.get(i).word +"\n");
                for (int j = 0; j < mask.length(); j++) {
                    if (mask.charAt(j) == '_') continue;
                    if (mask.charAt(j) != w.get(i).charAt(j)) {
                        thiz = false;
                        break;
                    }
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
    public static void addWord(String word, List<String> list) {
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
        } while (C2S(list.get(pivot), word) == '<' && C2S(list.get(pivot+1), word) == '>');
        list.add(pivot,word);
    }

    public static char C2S(String a, String b) {
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

    public static void main(String[] args) throws IOException {
        //Vector<Slovo> words=new Vector(10, 1);
        List<String> words = new ArrayList<>();
        String filename = "C:\\Users\\denpo\\IdeaProjects\\WerdFinder\\russian_nouns.txt";
        File f = new File(filename);
        //System.out.println("Reading from: "+filename);
        ReadFrom(f, words);
        System.out.println("Допро пожаловать в систему \"поиск слов\"!\n" +
                " На данный момент в базе хранится " + words.size() + " слов.\n" +
                "Для получения информации об использовании информационной системы введите /помощь.");
        Scanner sc = new Scanner(System.in);
        do {
            String input = sc.nextLine();
            if (input==null) continue;
            if (input.charAt(0) != '/') {
                System.out.println("Введите /маска для поиска по маске");
                continue;
            }
            if (input.equals("/маска")) {
                do {
                    System.out.println("Введите маску");
                    do input = sc.nextLine(); while (input==null);
                    if (input.equals("/меню") || input.equals("/стоп")) break;
                    System.out.println("Подождите, пожалуйста...");
                    List<String> found = FindWord(input, words);
                    System.out.println("Найдено слов: " + found.size());
                    for (String s : found) {
                        System.out.println(s);
                    }
                } while (true);
            }
            if (input.equals("/добавить")) {
                System.out.println("Введите слово для добавления");
                String word=sc.nextLine();
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
            if (input.equals("/стоп")) break;
        } while (true);
    }
}

/*static void sift(List<String> a, int n, int i) {
        int max = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && C2S(a.get(l), a.get(max)) == '>') max = l;
        if (r < n && C2S(a.get(r), a.get(max)) == '>') max = r;
        if (max != i) {
            String temp = a.get(i);
            a.set(i, a.get(max));
            a.set(max, temp);
            sift(a, n, max);
        }
    }*/
