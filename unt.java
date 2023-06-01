import java.util.ArrayList;
import java.util.concurrent.*;


class Car extends Thread {
    private String name;
    private int ind;
    private boolean flagsec;
    public Car(int name_, boolean second) {
        ind = name_;
        if (second) flagsec = true;
        name = Integer.toString(name_);
    }

    public void run()
    {
        boolean flag;
        if (flagsec) {
            try {
                flag = Main.drop.offer(name, (long)Main.w * 100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else flag = Main.drop.offer(name);
        if (flag){
            try {
                Thread.sleep((long) Main.t * 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long endtime = System.currentTimeMillis();
            long elapsed = endtime - Main.starttime;
            if (elapsed <= 6000)
            {
                Main.nums[ind] = 1;
            }
            Main.drop.remove(name);
        }

    }
}
class Main{
    static ArrayBlockingQueue<String> drop;
    public static int[] nums;
    public static int num;
    public static int w = 5;
    public static double t;
    public static long elapsed = 0;
    public static long starttime = 0;
    public static void main(String[] args){
        //num = 0;
        int lamb = 14;
        int n = 3;
        nums = new int[lamb];
        t = 60 * 0.4;
        ArrayList<Thread> ThreadArr = new ArrayList<>(lamb);
        drop = new ArrayBlockingQueue<String>(n);
        double p = lamb * (t/60);
        double temp = 1;
        for (int i = 1; i <= n; i++)
        {
            double temp1 = (Math.pow(p, i));
            int result = 1;
            for (int j = 1; j <= i; j++){
                result = result*j;
            }
            temp += temp1 / result;
        }
        double freechannel = 1/temp;
        int result = 1;
        for (int j = 1; j <= n; j++){
            result = result*j;
        }
        double failprob = Math.pow(p, n) / result * freechannel;//вероятность отказа
        System.out.println("Относительная пропускная способность: " + (1 - failprob));
        System.out.println("Абсолютная пропускная способность: " + ((1 - failprob)*lamb));

        System.out.println("-----------------------------");
        System.out.println("Без ожидания в " + w + " мин.");
        System.out.println("-----------------------------");
        starttime = System.currentTimeMillis();
        for (int i = 0; i < lamb; i++)
        {
            ThreadArr.add(i, new Car(i, false));
            ThreadArr.get(i).start();
            try {
                Thread.sleep(60/(lamb) * 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < lamb; i++)
        {
            try{
                ThreadArr.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        int sum = 0;
        for (int i = 0; i < lamb; i++) sum += nums[i];
        //System.out.println(sum);

        System.out.println("Относительная пропускная способность: " + (double)sum/lamb);
        System.out.println("Абсолютная пропускная способность: " + sum);


        for (int i = 0; i < lamb; i++) nums[i] = 0;
        starttime = System.currentTimeMillis();

        System.out.println("-----------------------------");
        System.out.println("С ожиданием в " + w + " мин.");
        System.out.println("-----------------------------");
        ThreadArr.clear();
        elapsed = 0;
        for (int i = 0; i < lamb; i++)
        {
            ThreadArr.add(i, new Car(i, true));
            ThreadArr.get(i).start();
            try {
                Thread.sleep(60/(lamb) * 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < lamb; i++)
        {
            try{
                ThreadArr.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        sum = 0;
        for (int i = 0; i < lamb; i++) sum += nums[i];
        System.out.println("Относительная пропускная способность: " + (double)sum/lamb);
        System.out.println("Абсолютная пропускная способность: " + sum);
    }
}
