package minilauncher.core;

import minilauncher.handler.Launcher;

public class Runner extends App {
    public static void run() {
        Log.debug("Start Runner.");
        while (running) {
            if (!App.layout.isVisible()) {
                if (!Launcher.isLaunching()) {
                    App.layout.setVisible(true);
                }
            } else {
                if (Launcher.isLaunching()) {
                    App.layout.setVisible(false);
                }
            }
        }
    }
}
