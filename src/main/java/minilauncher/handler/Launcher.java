package minilauncher.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;

import minilauncher.core.Log;

public class Launcher {
    private static ArrayList<Launcher> launchingPackages = new ArrayList<>();
    private Process process;
    private JButton button = null;
    private ActionListener[] originalListeners;
    private String oriText;
    private Color oriBackground;
    private Launcher(Packages.installPackage pack, JButton btn) {
        try {
            process = Runtime.getRuntime().exec("java -jar "+pack.gameDir+" --savedir \""+pack.saveDir+"\"");
            ProcessExitDetector processExitDetector = new ProcessExitDetector(process);
            processExitDetector.addProcessListener(new ProcessListener() {
                public void processFinished(Process process) {
                    removeLaunching();
                }
            });
            processExitDetector.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        launchingPackages.add(this);
        if (btn != null) {
            button = btn;
            originalListeners = button.getActionListeners();
            for (ActionListener al : originalListeners) button.removeActionListener(al);
            oriText = button.getText();
            oriBackground = button.getBackground();
            button.setText("Exit");
            button.setBackground(Color.BLUE);
            button.addActionListener(e -> {
                process.destroy();
                removeLaunching();
            });
        }
    }
    public void removeLaunching() {
        launchingPackages.remove(this);
        Log.debug("Removed launching.");
        if (button != null) {
            button.setText(oriText);
            button.setBackground(oriBackground);
            for (ActionListener al : button.getActionListeners()) button.removeActionListener(al);
            for (ActionListener al : originalListeners) button.addActionListener(al);
        }
    }
    public static boolean isLaunching() {
        return launchingPackages.size() != 0;
    }
    public static int launchingProcesses() {
        return launchingPackages.size();
    }
    public static void launchGame(Packages.installPackage pack) {
        new Launcher(pack, null);
    }
    public static void launchGame(Packages.installPackage pack, JButton btn) {
        new Launcher(pack, btn);
    }
    public static void launchGame(int index) {
        Packages.installPackage pack = Packages.packages.get(index);
        new Launcher(pack, null);
    }
}
