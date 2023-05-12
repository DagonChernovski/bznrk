import java.util.ArrayList;
import java.util.Arrays;

interface function { double calc(double x);}
class RectangleMethod extends Thread {
    int threadNum,precision;
    double a,b,h=0;
    function f;
    RectangleMethod(int _threadNo, int _p, double _a, double _b, function func) {
        threadNum = _threadNo;
        precision = _p;
        a = _a;
        b = _b;
        f=func;
    }
    @Override
    public void run(){
        h = (b - a) / precision;
        double x = 0;
        for (int i = threadNum - 1; i < precision; i += Main.numthreads) {
            x = a + h * i;
            Main.sumarray[threadNum - 1] += (h * f.calc(x));
        }
    }
}
class TrapezoidMethod extends Thread {
    int threadNum,precision;
    double a,b,h=0;
    function f;
    TrapezoidMethod(int _threadNo, int _p, double _a, double _b, function func) {
        threadNum = _threadNo;
        precision = _p;
        a = _a;
        b = _b;
        f=func;
    }
    @Override
    public void run(){
        h = (b - a) / precision;
        double x = 0;
        if (threadNum == 1) Main.sum += (f.calc(a)+ f.calc(a + h * precision))/2;
        for (int i = threadNum - 1; i < precision - 1; i += Main.numthreads) {
            x = a + h * i;
            Main.sumarray[threadNum - 1] += (f.calc(x));
        }
    }
}

class SimpsonMethod extends Thread{
    int threadNum,precision;
    double a,b,h=0;
    function f;
    SimpsonMethod(int _threadNo, int _p, double _a, double _b, function func) {
        threadNum = _threadNo;
        precision = _p;
        a = _a;
        b = _b;
        f = func;
    }
    @Override
    public void run(){
        h = (b - a) / precision;
        double x = 0;
        if (threadNum == 1) Main.sum += f.calc(a) + f.calc(b);
        for (int i = threadNum - 1; i < precision - 1; i += Main.numthreads) {
            x = a + h * i;
            if (i % 2 == 0) Main.sumarray[threadNum - 1] += (f.calc(x));
            else Main.sumarray2[threadNum - 1] += (f.calc(x));
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
    static final int PRECISION=(int)1e8;

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
                ThreadArr.add(i, new RectangleMethod(i + 1, PRECISION, a, b, ff));
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
            System.out.println(sum);
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
                ThreadArr.add(i, new TrapezoidMethod(i + 1, PRECISION, a, b, ff));
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
            sum *= (3./PRECISION);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Время работы: " + elapsed / 1000 + "с.");
            System.out.println(sum);
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
                ThreadArr.add(i, new SimpsonMethod(i + 1, PRECISION, a, b, ff));
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
            sum *= (((b-a)/PRECISION) / 3);
            long endtime = System.currentTimeMillis();
            double elapsed = endtime - starttime ;
            System.out.println("Время работы: " + elapsed / 1000 + "с.");
            System.out.println(sum);
            System.out.println("-----------------------------------");
        }
    }
}
