class JThread extends Thread{
    /*JThread(String name){
        super();
    }*/

    public void run(){
        String curThread = Thread.currentThread().getName();
        System.out.printf("%s started... \n", curThread);
        for (int i = (int) ((int) Math.pow(10, Main.n - 1) + (Math.pow(10, Main.n)/ (Main.numthreads) * (Integer.parseInt(curThread.substring(7, 8)) + 1))); i < Math.pow(10, Main.n) - (Math.pow(10, Main.n)/ (Main.numthreads) * (Integer.parseInt(curThread.substring(7, 8)) + 1)); i++)
        {
            int temp = i;
            boolean flag = true;
            for (int j = 1; j < Main.n / 2 + 1; j++)
            {
                int a = temp % 10;
                int b = temp / 10 % 10;
                if ((a != 0 && b != 0) && (Math.abs(a - b) == 1))
                    temp = temp / 100;
                else flag = false;
            }
            if (flag) {
                int d = i % 2;
                //System.out.println(d + " /" + i);

                if (d == 0) Main.evencount++;
                else Main.oddcount++;
            }

        }
        //System.out.println(Main.evencount);

    }
}

class RunnableThread implements Runnable{
    public void run(){
        String curThread = Thread.currentThread().getName();
        System.out.printf("%s started... \n", curThread);
        for (int i = (int) ((int) Math.pow(10, Main.n - 1) + (Math.pow(10, Main.n)/ (Main.numthreads) * (Integer.parseInt(curThread.substring(7, 8)) + 1))); i < Math.pow(10, Main.n) - (Math.pow(10, Main.n)/ (Main.numthreads) * (Integer.parseInt(curThread.substring(7, 8)) + 1)); i++)
        {
            int temp = i;
            boolean flag = true;
            for (int j = 1; j < Main.n / 2 + 1; j++)
            {
                int a = temp % 10;
                int b = temp / 10 % 10;
                if ((a != 0 && b != 0) && (Math.abs(a - b) == 1))
                    temp = temp / 100;
                else flag = false;
            }
            if (flag) {
                int d = i % 2;
                //System.out.println(d + " /" + i);

                if (d == 0) Main.evencount++;
                else Main.oddcount++;
            }

        }
        //System.out.println(Main.evencount);
    }
}

public class Main {
    public static int evencount = 0;
    public static int oddcount = 0;
    public static int n = 8;
    public static int numthreads = 1;
    static Thread[] ThreadArr = new Thread[numthreads];
    static Thread[] RunnableArr = new Thread[numthreads];
    public static void main(String[] args){

        if (n % 2 != 0) System.out.println("N is not a even number!");
        else {
            long starttime = System.currentTimeMillis();
            System.out.println("Main thread started...");
            //System.out.println(Math.pow(10, n - 1));
            for (int i = 0; i < numthreads; i++)
            {
                ThreadArr[i] = new JThread();
                //ThreadArr[i].setName("Thread-" + i);
                ThreadArr[i].start();
            };
            for (int i = 0; i < numthreads; i++)
            {
                try{
                    ThreadArr[i].join();

                }catch(Exception e){System.out.println(e);}
            }

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
                RunnableArr[i] = new JThread();
                RunnableArr[i].setName("Thread-" + i);
                RunnableArr[i].start();
            };
            for (int i = 0; i < numthreads; i++)
            {
                try{
                    RunnableArr[i].join();

                }catch(Exception e){System.out.println(e);}
            }
            endtime = System.currentTimeMillis();
            elapsed = endtime - starttime;
            System.out.println("Num of even numbers: " + evencount);
            System.out.println("Num of odd numbers: " + oddcount);
            System.out.println("Working time: " + elapsed + "ms.");

        }

    }

}
