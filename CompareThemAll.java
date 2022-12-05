package com.company;

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
        long startTime, endTime;
        long[][] timeElapsed=new long[6][3];
        int[][] commits=new int[6][3];
        System.out.println("Insert Sorting:\n");
        startTime=System.nanoTime();
        InsertSort(sorted);
        endTime=System.nanoTime();
        timeElapsed[0][0]=endTime-startTime;
        startTime=System.nanoTime();
        InsertSort(random);
        endTime=System.nanoTime();
        timeElapsed[0][1]=endTime-startTime;
        startTime=System.nanoTime();
        InsertSort(opposite);
        endTime=System.nanoTime();
        timeElapsed[0][2]=endTime-startTime;
        for (int i=0; i<3; i++) System.out.println(timeElapsed[0][i]);
        System.arraycopy(sorted, 0, sorted_, 0, 10000);
        System.arraycopy(random, 0, random_, 0, 10000);
        System.arraycopy(opposite, 0, opposite_, 0, 10000);

        System.out.println("Selection Sorting:\n");
        startTime=System.nanoTime();
        commits[1][0]=SelectionSort(sorted);
        endTime=System.nanoTime();
        timeElapsed[1][0]=endTime-startTime;
        startTime=System.nanoTime();
        commits[1][1]=SelectionSort(random);
        endTime=System.nanoTime();
        timeElapsed[1][1]=endTime-startTime;
        startTime=System.nanoTime();
        commits[1][2]=SelectionSort(opposite);
        endTime=System.nanoTime();
        timeElapsed[1][2]=endTime-startTime;
        for (int i=0; i<3; i++) System.out.println(timeElapsed[1][i]+" "+commits[1][i]);
        System.arraycopy(sorted_, 0, sorted, 0, 10000);
        System.arraycopy(random_, 0, random, 0, 10000);
        System.arraycopy(opposite_, 0, opposite, 0, 10000);



        System.out.println("Bubble Sorting:\n");
        //for (int i=0; i<10000; i++) System.out.println(random[i].getKey()+" "+random[i].getPass());
        startTime=System.nanoTime();
        commits[2][0]=BubbleSort(sorted);
        endTime=System.nanoTime();
        timeElapsed[2][0]=endTime-startTime;
        startTime=System.nanoTime();
        commits[2][1]=BubbleSort(random);
        endTime=System.nanoTime();
        timeElapsed[2][1]=endTime-startTime;
        //for (int i=0; i<10000; i++) System.out.println(random[i].getKey()+" "+random[i].getPass());
        startTime=System.nanoTime();
        commits[2][2]=BubbleSort(opposite);
        endTime=System.nanoTime();
        timeElapsed[2][2]=endTime-startTime;
        for (int i=0; i<3; i++) System.out.println(timeElapsed[2][i]+" "+commits[2][i]);
        //for (int i=0; i<10000; i++) System.out.println(sorted[i].getKey()+"   "+sorted_[i].getKey()+"  "+
         //       (sorted_[i].getKey()==sorted[i].getKey())+"               "
         //       +opposite[i].getKey()+"   "+opposite_[i].getKey()+"  "+((10001-opposite_[i].getKey())==opposite[i].getKey()));
        System.arraycopy(sorted_, 0, sorted, 0, 10000);
        System.arraycopy(random_, 0, random, 0, 10000);
        System.arraycopy(opposite_, 0, opposite, 0, 10000);

        System.out.println("Shaker sorting:\n");
        startTime=System.nanoTime();
        commits[3][1]=ShakerSort(random);
        endTime=System.nanoTime();
        timeElapsed[3][1]=endTime-startTime;
        startTime=System.nanoTime();
        commits[3][0]=ShakerSort(sorted);
        endTime=System.nanoTime();
        timeElapsed[3][0]=endTime-startTime;
        //for (int i=0; i<10000; i++) System.out.println(random[i].getKey()+" "+random[i].getPass());
        startTime=System.nanoTime();
        commits[3][2]=ShakerSort(opposite);
        endTime=System.nanoTime();
        timeElapsed[3][2]=endTime-startTime;
        for (int i=0; i<3; i++) System.out.println(timeElapsed[3][i]+" "+commits[3][i]);
        //for (int i=0; i<10000; i++) System.out.println(sorted[i].getKey()+"   "+sorted_[i].getKey()+"  "+
        //       (sorted_[i].getKey()==sorted[i].getKey())+"               "
        //       +opposite[i].getKey()+"   "+opposite_[i].getKey()+"  "+((10001-opposite_[i].getKey())==opposite[i].getKey()));
        System.arraycopy(sorted_, 0, sorted, 0, 10000);
        System.arraycopy(random_, 0, random, 0, 10000);
        System.arraycopy(opposite_, 0, opposite, 0, 10000);


        System.out.println("QUICK sorting:\n");
        startTime=System.nanoTime();
        //commits[3][1]=
        QuickSort(random, 0, 9999);
        endTime=System.nanoTime();
        timeElapsed[3][1]=endTime-startTime;
        startTime=System.nanoTime();
        QuickSort(sorted, 0, 9999);
        endTime=System.nanoTime();
        timeElapsed[3][0]=endTime-startTime;
        //for (int i=0; i<10000; i++) System.out.println(random[i].getKey()+" "+random[i].getPass());
        startTime=System.nanoTime();
        QuickSort(opposite, 0, 9999);
        endTime=System.nanoTime();
        timeElapsed[3][2]=endTime-startTime;
        for (int i=0; i<3; i++) System.out.println(timeElapsed[3][i]);//+" "+commits[3][i]);
        //for (int i=0; i<10000; i++) System.out.println(sorted[i].getKey()+"   "+sorted_[i].getKey()+"  "+
        //       (sorted_[i].getKey()==sorted[i].getKey())+"               "
        //       +opposite[i].getKey()+"   "+opposite_[i].getKey()+"  "+((10001-opposite_[i].getKey())==opposite[i].getKey()));
        System.arraycopy(sorted_, 0, sorted, 0, 10000);
        System.arraycopy(random_, 0, random, 0, 10000);
        System.arraycopy(opposite_, 0, opposite, 0, 10000);
    }

}
