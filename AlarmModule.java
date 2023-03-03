import

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Thread.sleep;

public class Main {
    static boolean a_pressed = false, h_pressed = false, m_pressed = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome!");
        int hours = 0, minutes = 0, seconds = 0, alHours = 0, alMinutes = 0;
        boolean alarmIsWorking = false;
        private static void add1sec() {
            seconds++;
            if (seconds<60) return;
            seconds=0; minutes++;
            if (minutes<60) return;
            minutes=0; hours++;
            if (hours<24) return;
            hours=0;
        }
        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                String key = e.getKeyText(e.getKeyCode());
                if (alarmIsWorking) if (key == "h") {
                    alHours = (alHours + 1) % 24;
                    try {
                        if (h_pressed) sleep(500);
                        else {
                            h_pressed = true;
                            sleep(1000);
                        }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (key == "m") {
                    {
                        alMinutes = (alMinutes + 1) % 60;
                        try {
                            if (m_pressed) sleep(500);
                            else {
                                m_pressed = true;
                                sleep(1000);
                            }
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String key = e.getKeyText(e.getKeyCode());
                //gonna redo it via case
                if (key == "a") {
                    alarmIsWorking = !alarmIsWorking;
                    System.out.println("ALARM SWITCH MODE. Press M and H to change time.");
                }
                if (key == "h") h_pressed = false;
                if (key == "m") m_pressed = false;
            }
        }
        while (true) {
            while (!alarmIsWorking) {
                System.out.printf("Current time: %20d:%20d\n", alHours, alMinutes);
                {
                    sleep(1000);
                    add1sec();
                }

            }
            while (alarmIsWorking)
            {

            }
        }
    }
}
