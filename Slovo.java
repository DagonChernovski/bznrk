package com.company;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import static com.company.speech.*;


enum speech {unknown, noun, adj, verb, interj, adverb};

public class Main {
    public static class Slovo {
        char[] word=new char[256];
        speech part;
        Slovo (char[] word, speech part)
        {
            this.word=word;
            this.part=part;
        }
    }
    static void ReadFrom(String filename, Vector<Slovo> w, speech what) throws IOException {
        try (FileReader reader=new FileReader(filename)) {
            StringBuilder result = new StringBuilder();
            char[] c = new char[20];;
            while (reader!=null)
                result.append('\n').append(c);
            System.out.println(result);
            }
            //Slovo nu=new Slovo(s, what);
           // w.add(nu);
            //System.out.print(Arrays.toString(w.lastElement().word) +" ");
        }
    }
    public void FindWord(String word, Vector <Slovo> w, int length, char[] mask) {

    }
    public static void main(String[] args) throws IOException {
        Vector<Slovo> words=new Vector(10, 1);
        String filename="C:\\Users\\denpo\\IdeaProjects\\WerdFinder\\Nouns.txt";
        System.out.println("Reading from: "+filename);
        ReadFrom(filename, words, noun);
        System.out.print(words.capacity());
        for (int i=0; i<words.capacity()-9; i++) {
            System.out.print(Arrays.toString(words.elementAt(i).word) +" ");
        }
    }
}
