package minilauncher.handler;

import java.awt.Color;

import javax.swing.JButton;

import minilauncher.core.Log;

public class LaunchingDetails {
    public JButton launchButton = new JButton("Launch");
    public boolean isDebug = false;
    public boolean isConsole = false;
    public static LaunchingDetails getDefaultDetails(Packages.installPackage pack) {
        LaunchingDetails launchingDetails = new LaunchingDetails();
        launchingDetails.launchButton.setForeground(Color.BLACK);
        launchingDetails.launchButton.setBackground(Color.GREEN);
        launchingDetails.launchButton.addActionListener(e -> {
            Log.debug("Launching game...");
            Launcher.launchGame(pack, launchingDetails.launchButton);
        });
        return launchingDetails;
    }
}
