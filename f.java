public class MyThread extends Thread {
        MyThread(String name){
            super(name);
        }
        public static array 
        public void run(){
            System.out.printf("%s started... \n", Thread.currentThread().getName());
            try{

            }
            catch(InterruptedException e){
                System.out.println("Thread has been interrupted");
            }
            System.out.printf("%s finished... \n", Thread.currentThread().getName());
        }
        public static String getThread() {
            return currentThread().getName();
        }
    public static void main(String[] args){
        int N=6;
        int
                left = (int) Math.pow(10,N),
                right = (int) (Math.pow(10,N+1)-1);
        int length=right-left;
        int a[]=new int[length];
        for (int i=0; i<length; i++) a[i]=i+left;
        Thread firstThread = Thread.currentThread();
        new MyThread("Thread 2").start();
        ThreadGroup threadGroup = currentThread().getThreadGroup();
        System.out.println("Thread: " + firstThread.getName());
        System.out.println("Thread: " + MyThread.getThread());
        System.out.println("Thread Group: " + threadGroup.getName());
        System.out.println("Parent Group: " + threadGroup.getParent().getName());
    }
};

