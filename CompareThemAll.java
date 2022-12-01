//package com.company;

import java.lang.String;
import java.lang.Math;
import java.util.Random;
//import java.util.Arrays;
//import java.util.Scanner;

public class CompareThemAll extends EfficientSorting {

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
            System.out.print("works    "+i+"   ");

            for (int j=0; j<50; j++) System.out.print(pass[j]);
            //if (pass==sorted[i-1].getPass());
            System.out.println();
        }
        //СТАДИЯ 1.5. Создадим копии массивов
        item sorted_[]=new item[10000];
        item random_[]=new item[10000];
        item opposite_[]=new item[10000];
        System.arraycopy(sorted, 0, sorted_, 0, 10000);
        System.arraycopy(random, 0, random_, 0, 10000);
        System.arraycopy(opposite, 0, opposite_, 0, 10000);
        //СТАДИЯ 2. СОРТИРОВКА
        long startTime, endTime;
        long[][] timeElapsed=new long[4][3];

        System.out.println("Cортировка выбором:\n");
        startTime=System.nanoTime();
        SelectionSort(sorted);
        endTime=System.nanoTime();
        timeElapsed[2][0]=endTime-startTime;
        System.out.println(random[666].getKey()+" "+random[666].getPass());
        System.out.println(random[9334].getKey()+" "+random[9334].getPass());
        startTime=System.nanoTime();
        SelectionSort(random);
        endTime=System.nanoTime();
        timeElapsed[2][1]=endTime-startTime;
        System.out.println(random[666].getKey()+" "+random[666].getPass());
        System.out.println(random[9334].getKey()+" "+random[9334].getPass());
        startTime=System.nanoTime();
        SelectionSort(opposite);
        endTime=System.nanoTime();
        timeElapsed[2][2]=endTime-startTime;
        for (int i=0; i<3; i++) System.out.println(timeElapsed[2][i]);
        System.arraycopy(random_, 0, random, 0, 10000);
        System.arraycopy(opposite_, 0, opposite, 0, 10000);
        System.out.println("Пузырьковая сортировка:\n");

        startTime=System.nanoTime();
        BubbleSort(sorted);
        endTime=System.nanoTime();
        timeElapsed[2][0]=endTime-startTime;
        System.out.println(random[10].getKey()+" "+random[10].getPass());
        System.out.println(random[9990].getKey()+" "+random[9990].getPass());
        startTime=System.nanoTime();
        BubbleSort(random);
        endTime=System.nanoTime();
        timeElapsed[2][1]=endTime-startTime;
        System.out.println(random[10].getKey()+" "+random[10].getPass());
        System.out.println(random[9990].getKey()+" "+random[9990].getPass());
        System.out.println(opposite[666].getKey()+" "+opposite[666].getPass());
        System.out.println(opposite[9334].getKey()+" "+opposite[9334].getPass());
        startTime=System.nanoTime();
        BubbleSort(opposite);
        endTime=System.nanoTime();
        timeElapsed[2][2]=endTime-startTime;
        System.out.println(opposite[666].getKey()+" "+opposite[666].getPass());
        System.out.println(opposite[9334].getKey()+" "+opposite[9334].getPass());
        for (int i=0; i<3; i++) System.out.println(timeElapsed[2][i]);
        System.arraycopy(random_, 0, random, 0, 10000);
        System.arraycopy(opposite_, 0, opposite, 0, 10000);


    }

}
