package minilauncher.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import minilauncher.core.Log;
import minilauncher.layout.LaunchConsole;

public class Launcher {
    private static ArrayList<Launcher> launchingPackages = new ArrayList<>();
    private Process process;
    private JButton button = null;
    private ActionListener[] originalListeners;
    private String oriText;
    private Color oriBackground;
    private LaunchConsole console;
    private Thread consoleOutputer;
    private Launcher(Packages.installPackage pack, JButton btn) {this(pack, btn, null);}
    private Launcher(Packages.installPackage pack, JButton btn, LauncherSettings settings) {
        try {
            boolean debug = false;
            if (settings != null) {
                if (settings.isConsole) {
                    console = new LaunchConsole();
                    console.setTitle("Console: "+pack.name+" ("+pack.version.toString()+")");
                    console.setVisible(true);
                }
                debug = settings.isDebug;
            }
            process = Runtime.getRuntime().exec("java -jar "+pack.gameDir+" --savedir \""+pack.saveDir+"\""+(debug? " --debug": ""));
            if (console != null) {
                console.inputField.addActionListener(e -> {
                    String input = console.inputField.getText()+"\n";
                    console.inputField.setText("");
                    OutputStream out = process.getOutputStream();
                    try {
                        out.write((input).getBytes());
                        out.flush();
                        console.printStream.print("> "+input);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                consoleOutputer = new Thread(new Runnable() {
                    public void run() {
                        while(true)
                        {
                            try {
                                process.getInputStream().transferTo(console.printStream);
                                process.getErrorStream().transferTo(console.printStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } );
                consoleOutputer.start();
            }
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
    public static class LauncherSettings {
        public boolean isDebug = false;
        public boolean isConsole = false;
    }
    public void removeLaunching() {removeLaunching(true);}
    public void removeLaunching(boolean removeSelf) {
        process.destroy();
        if (console != null) console.dispose();
        if (removeSelf) launchingPackages.remove(this);
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
        LauncherSettings settings = new LauncherSettings();
        settings.isDebug = pack.launchingDetails.isDebug;
        settings.isConsole = pack.launchingDetails.isConsole;
        new Launcher(pack, btn, settings);
    }
    public static void launchGame(Packages.installPackage pack, JButton btn, LauncherSettings settings) {
        new Launcher(pack, btn, settings);
    }
    public static void launchGame(int index) {
        Packages.installPackage pack = Packages.packages.get(index);
        new Launcher(pack, null);
    }
    public static void removeAllLaunching() {
        for (Launcher launcher : launchingPackages) {
            launcher.removeLaunching(false);
        }
    }
}
