package com.company;

class JThread extends Thread{
        /*JThread(String name){
            super();
        }*/
    public void run(){
        String curThread = Thread.currentThread().getName();
        long preStart=(long)Math.pow(10, Main.n - 1); //первое
        long preEnd=(long)(Math.pow(10, Main.n)-Math.pow(10, Main.n - 1));
        int range=Integer.parseInt(curThread.substring(7));
        long start = (preStart + preEnd/ Main.numthreads * range),
                end = (preStart + preEnd/ Main.numthreads * (range+1));
        if (end>Math.pow(10, Main.n)) end=(long)Math.pow(10, Main.n);
        System.out.println(curThread+" started... - from "+ start+" "+end);
        for (long i = start; i < end; i++)
        {
            //i перебирается от 10^14
            long temp = i;
            boolean flag = true;
            for (int j = 1; j < Main.n; j++)
            {
                long a = temp % 10;
                long b = temp / 10 % 10;
                if (Math.abs(a-b)==1 && Math.min(a,b)!=0)
                    temp = temp / 10;
                else flag = false;
            }
            if (flag) {
                long d = i % 2;
                //System.out.println(d + " /" + i);
                if (d == 0) Main.evencount++;
                else Main.oddcount++;
                //System.out.printf("%s %d\n", curThread, i);
            }
            //System.out.printf("%d ", i);
        }
        //System.out.println(Main.evencount);

    }
}

class RunnableThread implements Runnable{
    private boolean isInterrupted;
    private int curThread;
    public RunnableThread(int n) {
        super();
        curThread=n;
    }
    public void run(){
        isInterrupted=false;
        //System.out.println(curThread+" started...");
        long preStart=(long)Math.pow(10, Main.n - 1);
        long preEnd=(long)(Math.pow(10, Main.n)-Math.pow(10, Main.n - 1));
        int range=curThread;
        long start= preStart + preEnd / Main.numthreads * range,
                end=preStart + preEnd / Main.numthreads * (range+1);
        if (end>Math.pow(10, Main.n)) end=(long)Math.pow(10, Main.n);
        System.out.println(curThread+" started... - from "+ start+" "+end);
        for (long i = start; i < end; i++) {
            long temp = i;
            boolean flag = true;
            for (int j = 1; j < Main.n; j++)
            {
                long a = temp % 10;
                long b = temp / 10 % 10;
                if (Math.abs(a-b)==1 && Math.min(a,b)!=0)
                    temp = temp / 10;
                else flag = false;
            }
            if (flag) {
                long d = i % 2;
                System.out.println(i);
                //System.out.println(d + " /" + i);
                if (d == 0) Main.evencount++;
                else Main.oddcount++;

            }
        }
        System.out.println(curThread+" finished...");
        this.interrupt();
        //System.out.println(Main.evencount);
    }
    public void interrupt() {
        isInterrupted=true;
    }
    public void join() {
        while (!isInterrupted) {};
    }
}

public class Main {
    public static long evencount = 0,oddcount = 0;
    public static int n = 8, numthreads = 6;
    static Thread[] ThreadArr = new JThread[numthreads];
    static Runnable[] RunnableArr = new RunnableThread[numthreads];
    //static Thread RunnableA = new Thread(RunnableArr);
    public static void main(String[] args){


            long starttime = System.currentTimeMillis();
            System.out.println("Main thread started...");
            //System.out.println(Math.pow(10, n - 1));
            for (int i = 0; i < numthreads; i++)
            {
                ThreadArr[i] = new JThread();
                ThreadArr[i].start();
            };
            for (int i = 0; i < numthreads; i++)
            {try{ThreadArr[i].join();}catch(Exception e){System.out.println(e);}}

            System.out.println("Main thread finished...");
            System.out.println("Num of even numbers: " + evencount);
            System.out.println("Num of odd numbers: " + oddcount);
            long endtime = System.currentTimeMillis();
            long elapsed = endtime - starttime;
            System.out.println("Working time: " + elapsed + "ms.");
            System.out.println("-----------------------------------");
            evencount = 0;
            oddcount = 0;
            starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++)
            {
                RunnableArr[i]=new RunnableThread(i);
                RunnableArr[i].run();
            };
            for (int i = 0; i < numthreads; i++)
            {
                try {
                    RunnableArr[i].notify();
                } catch(Exception e) {System.out.println(e);}
            }
            endtime = System.currentTimeMillis();
            elapsed = endtime - starttime;
            System.out.println("Num of even numbers: " + evencount);
            System.out.println("Num of odd numbers: " + oddcount);
            System.out.println("Working time: " + elapsed + "ms.");

    }

}
