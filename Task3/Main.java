import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Task2 extends Thread {
    private char character;
    int messageNumbers;
    SyncState state;
    private int index;
    private static volatile String output = "";
    Task2(int index, char output, SyncState state, int charNumbers) {
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
                    File.writer.write(output + '\n');
                    File.writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            state.setState(index +1);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        SyncState state = new SyncState(0);
        int n = 3;
        int numberOfMassages = 4;
        for (int i = 0; i < n; i++) {
            Task2 task2 = new Task2(i, ((char)('A' + i)), state , numberOfMassages);
            threads.add(task2);
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
        try {
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
