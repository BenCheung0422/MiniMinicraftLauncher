package minilauncher.handler;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import minilauncher.core.Log;

public class StatusBar {
    private static JLabel statusBar;
    private static Runnable setVisible;
    private static Runnable setInvisible;
    private static Timer timer = new Timer();
    private static TimerTask curTimerTask = null;
    public static void setStatusBar(JLabel label) {
        statusBar = label;
    }
    
    public static void setVisibleFunction(Runnable r) {
        setVisible = r;
    }
    public static void setInvisibleFunction(Runnable r) {
        setInvisible = r;
    }

    public static void initBar() {
        setInvisible.run();
        statusBar.setBackground(Color.BLACK);
        statusBar.setForeground(Color.WHITE);
        statusBar.setFont(new Font(Font.SERIF, Font.BOLD, 20));
    }

    public static void visible(int ms) {
        if (curTimerTask != null) curTimerTask.cancel();
        curTimerTask = new TimerTask() {
            @Override
            public void run() {
                invisible();
                curTimerTask = null;
            }
        };
        setVisible.run();
        timer.schedule(curTimerTask, ms);
        Log.debug("Showed Status Bar.");
    }

    public static void invisible() {
        setInvisible.run();
        Log.debug("Hided Status Bar.");
    }

    public static void setStatus(String text) {
        statusBar.setText(text);
        Log.debug("Updated status of Status Bar.");
    }
}
