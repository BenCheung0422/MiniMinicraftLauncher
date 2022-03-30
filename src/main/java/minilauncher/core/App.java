package minilauncher.core;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import minilauncher.handler.Packages;
import minilauncher.layout.Layout;
import minilauncher.layout.mainLayout.Loading;
import minilauncher.layout.mainLayout.MainPage;

public class App {
    public static boolean isDebug = false;
    public static boolean running = true;
    public static String dataDir;
    public static Layout layout;
	static String OS;
	private static String localAppDir;
	static String systemAppDir;

    public static void exit() {
        running = false;
    }
    public static void main(String[] args) {
        resolveArgs(args);
        Layout mainLayout = new Layout(new Layout.Properties());
        {
            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("Options");
            menu.setMnemonic(KeyEvent.VK_O);
            JMenuItem exit = new JMenuItem("Exit");
            exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
            exit.addActionListener(e -> {
                mainLayout.dispatchEvent(new WindowEvent(mainLayout, WindowEvent.WINDOW_CLOSING));;
            });
            menu.add(exit);
            menuBar.add(menu);
            menuBar.setBackground(Color.GRAY);
            mainLayout.setJMenuBar(menuBar);
        }
        mainLayout.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainLayout.setLayout(new BorderLayout()); // Sets the layout of the window
        mainLayout.setLocationRelativeTo(null);
        mainLayout.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {
                Log.debug("Window Closing.");
                exit();
            }
            public void windowClosed(WindowEvent e) {Log.debug("Window Closed.");}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
        Loading.loadLayout(mainLayout);
        mainLayout.setVisible(true);
        {
            OS = System.getProperty("os.name").toLowerCase();
            //System.out.println("os name: \"" +os + "\"");
            String local = "playminicraft/launchers/MiniLauncher";
            
            if(OS.contains("windows")) // windows
                systemAppDir = System.getenv("APPDATA");
            else {
                systemAppDir = System.getProperty("user.home");
                if(!OS.contains("mac"))
                    local = "."+local; // linux
            }
            
            localAppDir = "/"+local;
            dataDir = systemAppDir + localAppDir;
            Log.debug("Determined gameDir: " + dataDir);
            
            File testFile = new File(dataDir);
            testFile.mkdirs();
        }
        FileHandler.makeDirs();
        Packages.init();
        MainPage.loadLayout(mainLayout);
        layout = mainLayout;
        Runner.run();
        Log.debug("Application exits.");
        System.exit(0);
    }
    private static void resolveArgs(String[] args) {
        for (String arg : args) {
            switch(arg.toLowerCase()) {
                case "--debug":
                    isDebug = true;
            }
        }
    }
}
