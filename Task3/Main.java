import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

class ThreadWriter extends Thread {
    private int index; //First Second Third;
    private int number;
    int messageNumbers;
    Semaphore sem;
    private static volatile String output = "";

    ThreadWriter(int index, String output, Semaphore sem, int charNumbers) {
        this.messageNumbers = charNumbers;
        this.sem = sem;
        this.index = index;
        this.output = output;
    }

    @Override
    public void run() {
        for (int j = 0; j < messageNumbers; j++) {
            try {
                sem.acquire();
                File.writer.write(output + "->" + j + '\n');
                sem.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class Alphabet extends Thread {
    private char character;
    int messageNumbers;
    SyncState state;
    private int index;
    private static volatile String output = "";
    Alphabet(int index, char output, SyncState state, int charNumbers) {
        this.messageNumbers = charNumbers;
        this.state = state;
        this.index = index;
        character = output;
    }

    @Override
    public void run() {

        synchronized (state) {
            while(state.getState() != index) {
                try {
                    state.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < messageNumbers; j++) {
                output += character;
                try {
                    File.aWriter.write(output + '\n');
                    File.aWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            state.setState(index +1);
        }
    }
}
public class Main {
    private static String[] threadNames =new String[]{"First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth","Ninth","Tenth"};
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        SyncState state = new SyncState(0);
        int n = 3;
        int numberOfMessages = 4;
        for (int i = 0; i < n; i++) {
            Alphabet alal = new Alphabet(i, ((char)('A' + i)), state, numberOfMessages);
            threads.add(alal);
        }

        for (Thread th : threads) {
            th.start();
        }
        for (Thread th : threads) {
            try {
                th.join();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Alphabet written");
        threads.clear();
        Semaphore sem=new Semaphore(1);
        for (int i=0; i<n; i++) {
            threads.add(new ThreadWriter(i,threadNames[i],sem,numberOfMessages));
            System.out.println(threads.get(i).getName()+" initialized");
        }
        for (Thread th : threads) {
            th.start();
        }
        for (Thread th : threads) {
            try {
                th.join();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Threader written");
        try {
            File.aWriter.close();
            File.writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class SyncState {

    private volatile int state;

    public SyncState(int state) {
        this.state = state;
    }

    synchronized public int getState() {
        return state;
    }

    synchronized public void setState(int state) {
        this.state = state;
    }
}
