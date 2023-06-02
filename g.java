package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

interface function { double calc(double x);}
class RectangleMethod extends Thread {
    int threadNum,precision;
    double a,b,h=0;
    function f;
    double sum=0;
    Semaphore sem;
    RectangleMethod(int _threadNo, int _p, double _a, double _b, function func, Semaphore sema) {
        threadNum = _threadNo;
        precision = _p;
        a = _a;
        b = _b;
        f=func;
        sem=sema;
    }
    @Override
    public void run(){
        h = (b - a) / precision;
        double x = 0;
        for (int i = threadNum - 1; i < precision; i += Main.numthreads) {
            x = a + h * i;
            sum += (h * f.calc(x));
        }
        try {
            sem.acquire();
            Main.sums[0]+=sum;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sem.release();
    }
}
class TrapezoidMethod extends Thread {
    int threadNum,precision;
    double a,b,h=0;
    function f;
    double sum=0;
    Semaphore sem;
    TrapezoidMethod(int _threadNo, int _p, double _a, double _b, function func,Semaphore sema) {
        threadNum = _threadNo;
        precision = _p;
        a = _a;
        b = _b;
        f=func;
        sem=sema;
    }
    @Override
    public void run(){
        h = (b - a) / precision;
        double x = 0;
        if (threadNum == 1) sum += (f.calc(a)+ f.calc(a + h * precision))/2;
        for (int i = threadNum - 1; i < precision - 1; i += Main.numthreads) {
            x = a + h * i;
            sum += (f.calc(x));
        }
        try {
            sem.acquire();
            Main.sumarray[0]+=sum;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sem.release();
    }
}

class SimpsonMethod extends Thread{
    int threadNum,precision;
    double a,b,h=0;
    function f;
    double sum=0,sum1=0,sum2=0;
    Semaphore sem;
    SimpsonMethod(int _threadNo, int _p, double _a, double _b, function func, Semaphore sema) {
        threadNum = _threadNo;
        precision = _p;
        a = _a;
        b = _b;
        f = func;
        sem=sema;
    }
    @Override
    public void run(){
        h = (b - a) / precision;
        double x = 0;
        if (threadNum == 1) sum += f.calc(a) + f.calc(b);
        for (int i = threadNum - 1; i < precision - 1; i += Main.numthreads) {
            x = a + h * i;
            if (i % 2 == 0) sum1 += (f.calc(x));
            else sum2 += (f.calc(x));
        }
        sum+=sum1*2+sum2*4;
        try {
            sem.acquire();
            Main.sums[2]+=sum;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sem.release();
    }
}

public class Main {
    //public static AtomicInteger sum = new AtomicInteger(0);
    public static double sum = 0;
    public static int numthreads = 1;
    static ArrayList<Thread> ThreadArr = new ArrayList<>(1);
    //static Thread[] ThreadArr = new Thread[8];
    public static int[] threadnum = {24, 3, 6, 12};
    static double[] sums=new double[3];
    static double[] sumarray = new double[numthreads];
    static double[] sumarray2 = new double[numthreads];
    static function ff=(x)->(Math.pow(Math.log(x),2)/x); // function interface
    static double a=1, b=4; //integral edges
    static final int PRECISION=(int)1e9;
    static Semaphore sem=new Semaphore(1);
    public static double sum1 = 0, sum2 = 0;
    public static void main(String[] args) {
        System.out.println("-----------------------------------");
        System.out.println("Метод прямоугольника");
        System.out.println("-----------------------------------");

        //(Math.pow(Math.E, x)*(1 + Math.sin(x))) / (1 + Math.cos(x));
        //(x)->(Math.pow(Math.log(x),2)/x);
        for (int j = 0; j < threadnum.length ; j++) {
            sum = 0;
            numthreads = threadnum[j];
            sumarray = new double[numthreads];
            long starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++) {
                ThreadArr.add(i, new RectangleMethod(i + 1, PRECISION, a, b, ff,sem));
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
            System.out.println("Время работы: " + elapsed / 1000 + "с., потоков = " + numthreads);
            System.out.println(sums[j]);
            System.out.println("-----------------------------------");
        }
        System.out.println("-----------------------------------");
        System.out.println("Метод трапеции");
        System.out.println("-----------------------------------");

        Arrays.fill(sumarray, 0);
        for (int j = 0; j < threadnum.length; j++) {
            sum = 0;
            numthreads = threadnum[j];
            sumarray = new double[numthreads];
            long starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++) {
                ThreadArr.add(i, new TrapezoidMethod(i + 1, PRECISION, a, b, ff,sem));
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
            sums[1] *= (3./PRECISION);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Время работы: " + elapsed / 1000 + "с.");
            System.out.println(sums[j]);
            System.out.println("-----------------------------------");
        }
        System.out.println("-----------------------------------");
        System.out.println("Метод Симпсона");
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
                ThreadArr.add(i, new SimpsonMethod(i + 1, PRECISION, a, b, ff,sem));
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
            sums[2] *= (((b-a)/PRECISION) / 3);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime;
            System.out.println("Время работы: " + elapsed / 1000 + "с.");
            System.out.println(sums[j]);
            System.out.println("-----------------------------------");
        }
    }
}
