package com.company;

import java.util.ArrayList;
import java.util.Arrays;

interface function { double calc(double x);}
class RectangleMethod extends Thread{
    int number,count;
    double a,b,h=0;
    function f;
    RectangleMethod(int num1, int num2, double _a, double _b, function func){
        number = num1;
        count = num2;
        a = _a;
        b = _b;
        f=func;
    }
    @Override
    public void run(){
        h = (b - a) / count;
        double x = 0;
        for (int i = number - 1; i < count; i += Main.numthreads) {
            x = a + h * i;
            Main.sumarray[number - 1] += (h * f.calc(x));
        }
    }
}
class TrapezoidMethod extends Thread{
    int number,count;
    double a,b,h=0;
    function f;
    TrapezoidMethod(int num1, int num2, double _a, double _b, function func){
        number = num1;
        count = num2;
        a = _a;
        b = _b;
        f=func;
    }
    @Override
    public void run(){
        h = (b - a) / count;
        double x = 0;
        if (number == 1) Main.sum += (f.calc(a)+ f.calc(a + h * count))/2;
        for (int i = number - 1; i < count - 1; i += Main.numthreads) {
            x = a + h * i;
            Main.sumarray[number - 1] += (f.calc(x));
        }
    }
}

class SimpsonMethod extends Thread{
    int number,count;
    double a,b,h=0;
    function f;
    SimpsonMethod(int num1, int num2, double _a, double _b, function func) {
        number = num1;
        count = num2;
        a = _a;
        b = _b;
        f = func;
    }
    @Override
    public void run(){
        h = (b - a) / count;
        double x = 0;
        if (number == 1) Main.sum += f.calc(a) + f.calc(b);
        for (int i = number - 1; i < count - 1; i += Main.numthreads) {
            x = a + h * i;
            if (i % 2 == 0) Main.sumarray[number - 1] += (f.calc(x));
            else Main.sumarray2[number - 1] += (f.calc(x));
        }
    }
}

public class Main {
    //public static AtomicInteger sum = new AtomicInteger(0);
    public static double sum = 0;
    public static int numthreads = 1;
    static ArrayList<Thread> ThreadArr = new ArrayList<>(1);
    //static Thread[] ThreadArr = new Thread[8];
    public static int[] threadnum = {1, 3, 6, 12};
    static double[] sumarray = new double[numthreads];
    static double[] sumarray2 = new double[numthreads];
    static function ff=(x)->(Math.pow(Math.log(x),2)/x); // function interface
    static double a=1, b=4; //integral edges
    static final int precision=(int)1e8;
    public static double sum1 = 0, sum2 = 0;
    public static void main(String[] args) {
        System.out.println("-----------------------------------");
        System.out.println("Rectangle Method");
        System.out.println("-----------------------------------");

        //(Math.pow(Math.E, x)*(1 + Math.sin(x))) / (1 + Math.cos(x));
        //(x)->(Math.pow(Math.log(x),2)/x);
        for (int j = 0; j < threadnum.length ; j++) {
            sum = 0;
            numthreads = threadnum[j];
            sumarray = new double[numthreads];
            long starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++) {
                ThreadArr.add(i, new RectangleMethod(i + 1, precision, a, b, ff));
                //ThreadArr[i].setName("Thread-" + i);
                (ThreadArr.get(i)).start();
            }
            for (int i = 0; i < numthreads; i++) {
                try {
                    ThreadArr.get(i).join();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            for (int x = 0; x < sumarray.length; x++) sum += sumarray[x];
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Working time: " + elapsed / 1000 + "s., Threads num = " + numthreads);
            System.out.println(sum);
            System.out.println("-----------------------------------");
        }
        System.out.println("-----------------------------------");
        System.out.println("Trapezoid Method");
        System.out.println("-----------------------------------");

        Arrays.fill(sumarray, 0);
        for (int j = 0; j < threadnum.length ; j++) {
            sum = 0;
            numthreads = threadnum[j];
            sumarray = new double[numthreads];
            long starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++) {
                ThreadArr.add(i, new TrapezoidMethod(i + 1, precision, a, b, ff));
                //ThreadArr[i].setName("Thread-" + i);
                ThreadArr.get(i).start();
            }

            for (int i = 0; i < numthreads; i++) {
                try {
                    ThreadArr.get(i).join();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            for (int x = 0; x < sumarray.length; x++) sum += sumarray[x];
            sum *= (3./precision);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Working time: " + elapsed / 1000 + "s.");
            System.out.println(sum);
            System.out.println("-----------------------------------");
        }
        System.out.println("-----------------------------------");
        System.out.println("Simpson Method");
        System.out.println("-----------------------------------");

        Arrays.fill(sumarray, 0);
        Arrays.fill(sumarray2, 0);

        for (int j = 0; j < threadnum.length ; j++) {
            sum = 0;
            sum1 = 0;
            sum2 = 0;
            numthreads = threadnum[j];
            sumarray = new double[numthreads];
            sumarray2 = new double[numthreads];
            long starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++) {
                ThreadArr.add(i, new SimpsonMethod(i + 1, precision, a, b, ff));
                //ThreadArr[i].setName("Thread-" + i);
                ThreadArr.get(i).start();
            }

            for (int i = 0; i < numthreads; i++) {
                try {
                    ThreadArr.get(i).join();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            for (int x = 0; x < sumarray.length; x++) sum1 += sumarray[x];
            for (int x = 0; x < sumarray2.length; x++) sum2 += sumarray2[x];
            sum += sum1 * 2;
            sum += sum2 * 4;
            sum *= ((3./precision) / 3);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Working time: " + elapsed / 1000 + "s.");
            System.out.println(sum);
            System.out.println("-----------------------------------");
        }
    }
}
