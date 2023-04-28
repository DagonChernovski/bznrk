import java.util.ArrayList;
import java.util.Arrays;

interface function { double calc(double x);}
class RectangleMethod extends Thread{
    int number = 0;
    int count = 0;
    double h = 0;
    double a = 0;
    double b = 0;
    RectangleMethod(int num1, int num2, double num3, double num4){
        number = num1;
        count = num2;
        a = num3;
        b = num4;
    }

    double func(double number){
        return (Math.pow(Math.E, number)*(1 + Math.sin(number))) / (1 + Math.cos(number));
    }
    @Override
    public void run(){
        h = (b - a) / count;
        double x = 0;
        for (int i = number - 1; i < count; i += Main.numthreads) {
            x = a + h * i;
            Main.sumarray[number - 1] += (h * func(x));
        }
    }
}

class TrapezoidMethod extends Thread{
    int number = 0;
    int count = 0;
    double h = 0;
    double a = 0;
    double b = 0;
    TrapezoidMethod(int num1, int num2, double num3, double num4 ){
        number = num1;
        count = num2;
        a = num3;
        b = num4;
    }

    double func(double number){
        return (Math.pow(Math.E, number)*(1 + Math.sin(number))) / (1 + Math.cos(number));
    }
    @Override
    public void run(){
        h = (b - a) / count;
        double x = 0;
            if (number == 1) Main.sum += (func(a)+ func(a + h * count)) / 2;
            for (int i = number - 1; i < count - 1; i += Main.numthreads) {
                x = a + h * i;
                Main.sumarray[number - 1] += (func(x));
            }

    }
}

class SimpsonMethod extends Thread{
    int number = 0,count = 0;
    double h = 0,a = 0,b = 0;
    SimpsonMethod(int num1, int num2, double num3, double num4){
        number = num1;
        count = num2;
        a = num3;
        b = num4;
    }

    double func(double number){
        return (Math.pow(Math.E, number)*(1 + Math.sin(number))) / (1 + Math.cos(number));
    }
    @Override
    public void run(){
        h = (b - a) / count;
        double x = 0;
            if (number == 1) Main.sum += func(a) + func(b);
            for (int i = number - 1; i < count - 1; i += Main.numthreads) {
                x = a + h * i;
                if (i % 2 == 0) Main.sumarray[number - 1] += (func(x));
                else Main.sumarray2[number - 1] += (func(x));
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

    public static double sum1 = 0, sum2 = 0;
    public static void main(String[] args) {
        System.out.println("-----------------------------------");
        System.out.println("Rectangle Method");
        System.out.println("-----------------------------------");
        for (int j = 0; j < threadnum.length ; j++) {
            sum = 0;
            numthreads = threadnum[j];
            sumarray = new double[numthreads];
            long starttime = System.currentTimeMillis();
            for (int i = 0; i < numthreads; i++) {
                ThreadArr.add(i, new RectangleMethod(i + 1, (int)1e8, 0, 1.5));
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
                ThreadArr.add(i, new TrapezoidMethod(i + 1, (int)1e8, 0, 1.5));
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
            sum *= (1.5/(int)1e8);
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
                ThreadArr.add(i, new SimpsonMethod(i + 1, (int)1e8, 0, 1.5));
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
            sum *= ((1.5/(int)1e8) / 3);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Working time: " + elapsed / 1000 + "s.");
            System.out.println(sum);
            System.out.println("-----------------------------------");



        }

    }
}
