package com.company;

import java.lang.String;
import java.lang.Math;
import java.util.Random;
//import java.util.Arrays;
//import java.util.Scanner;

enum measure {timed, assigns};

public class CompareThemAll extends CTracker {

    static long SortWay(item[] a, int i) {
        long ret;
        switch(i) {
            case 0: return InsertSort(a);
            case 1: return SelectionSort(a);
            case 2: return BubbleSort(a);
            case 3: return ShakerSort(a);
            default: return QuickSort(a,0,10000);
        }
    }
    static int[] SortC(item[] a, int i) {
        switch(i) {
            case 0: return InsertSortC(a);
            case 1: return SelectionSortC(a);
            case 2: return BubbleSortC(a);
            case 3: return ShakerSortC(a);
            default: return QuickSortC(a,0,10000);
            //default: throw new Exception("You picked the wrong number fool");
        }
    }
    public static void main(String[] args) {

        item sorted[]=new item[10000];
        item random[]=new item[10000];
        item opposite[]=new item[10000];
        Character[] pass=new Character[50];
        //СТАДИЯ 1. ВВОД
        Random ran=new Random();
        for (int i=0; i<10000; i++)
        {
            for (int j=0; j<50; j++) {pass[j] = (char)(ran.nextInt(79) + 43);}
            sorted[i] = new item();
            opposite[i] = new item();
            random[i]=new item();
            sorted[i].setItem(i, pass);
            opposite[i].setItem(10000-i, pass);
            random[i].setItem((int)(Math.random()*10000), pass);
            //System.out.print("works    "+i+"   ");

            //for (int j=0; j<50; j++) System.out.print(pass[j]);
            //if (pass==sorted[i-1].getPass());
            //System.out.println();
        }
        //СТАДИЯ 1.5. Создадим копии массивов
        item sorted_[]=new item[10000];
        item random_[]=new item[10000];
        item opposite_[]=new item[10000];
        System.arraycopy(sorted, 0, sorted_, 0, 10000);
        System.arraycopy(random, 0, random_, 0, 10000);
        System.arraycopy(opposite, 0, opposite_, 0, 10000);
        //СТАДИЯ 2. СОРТИРОВКА
        item[][] e =new item[3][];
        e[0]=sorted;
        e[1]=random;
        e[2]=opposite;
        item[][] e_ =new item[3][];
        e_[0]=sorted;
        e_[1]=random;
        e_[2]=opposite;
        long[][] timeElapsed=new long[6][3];
        int[][][] inner_efficiency=new int[6][3][2];
        for (int i=0; i<6; i++) {
            for (int j = 0; j < 3; j++) {
                timeElapsed[i][j] = SortWay(e[j], i);
                System.arraycopy(e_[j], 0, e[j], 0, 10000);
                inner_efficiency[i][j] = SortC(e[j], i);
                System.arraycopy(e_[j], 0, e[j], 0, 10000);
            }
        }
        //for (int i=0; i<10000; i++) System.out.println(random[i].getKey()+" "+random[i].getPass());
        //ВЫВОД ВСЕХ ДАННЫХ!!!
        String sorting_type[]={"InsertSort","SelectionSort","BubbleSort","ShakerSort","QuickSort"};
        for (int i=0; i<5; i++) {
            System.out.println(sorting_type[i]);
            for (int j = 0; j < 3; j++)
                System.out.println(timeElapsed[i][j]);
        }
    }

}

/* for (int i=0; i<10000; i++) System.out.println(sorted[i].getKey()+"   "+sorted_[i].getKey()+"  "+
                (sorted_[i].getKey()==sorted[i].getKey())+"               "
                +opposite[i].getKey()+"   "+opposite_[i].getKey()+"  "+((10001-opposite_[i].getKey())==opposite[i].getKey()));

 */
