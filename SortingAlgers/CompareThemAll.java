package com.company;

import java.lang.String;
import java.lang.Math;
import java.util.Random;

public class Main extends CTracker {

    static long SortWay(item[] a, int i) {
        switch(i) {
            case 0: return InsertSort(a);
            case 1: return SelectionSort(a);
            case 2: return BubbleSort(a);
            case 3: return ShakerSort(a);
            case 4: return QuickSort(a,0,9999);
            case 5: return PyramidalSort(a);
            default: return 0;
        }
    }
    static long[] SortC(item[] a, int i) {
        switch(i) {
            case 0: return InsertSortC(a);
            case 1: return SelectionSortC(a);
            case 2: return BubbleSortC(a);
            case 3: return ShakerSortC(a);
            case 4: return QuickSortC(a,0,9999);
            default: return PyramidalSortC(a);
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
            random[i].setItem((int)(Math.random()*1000000), pass);
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
        e_[0]=sorted_;
        e_[1]=random_;
        e_[2]=opposite_;
        long[][] timeElapsed=new long[6][3];
        long[][][] inner_efficiency=new long[6][3][2];
        for (int i=0; i<6; i++) {
            for (int j = 0; j < 3; j++) {
                timeElapsed[i][j] = SortWay(e[j], i);
                if (i==1 && j==1) for (int k=0; k<10000; k++) System.out.println(e[j][k].key+" "+e_[j][k].key);
                System.arraycopy(e_[j], 0, e[j], 0, 10000);
                inner_efficiency[i][j] = SortC(e[j], i);
                System.arraycopy(e_[j], 0, e[j], 0, 10000);
            }
        }
        //ВЫВОД ВСЕХ ДАННЫХ!!!
        String sorting_type[]={"InsertSort","SelectionSort","BubbleSort","ShakerSort","QuickSort","PyramidalSort"};
        for (int i=0; i<6; i++) {
            System.out.println(sorting_type[i]);
            for (int j = 0; j < 3; j++)
                System.out.println("Time elapsed: "+timeElapsed[i][j]+
                        " Comparisons:"+inner_efficiency[i][j][0]+
                        " Assignations: "+inner_efficiency[i][j][1]);
        }
    }
}

//if (i==5 && j==2) for (int k=0; k<10000; k++) System.out.println(e_[j][k].getKey()+" "+e[j][k].getKey());
