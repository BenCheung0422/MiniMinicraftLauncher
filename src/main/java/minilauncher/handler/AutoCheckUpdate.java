package minilauncher.handler;

import java.util.ArrayList;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JMenuItem;

import minilauncher.core.Network;
import minilauncher.core.VersionInfo;
import minilauncher.layout.mainLayout.MainPage;

public class AutoCheckUpdate {
    private static ArrayList<AutoCheckUpdate> updateHandlers = new ArrayList<>();
    private String name;
    private boolean autoUpdate;
    private JMenuItem menuItem = new JMenuItem();
    private AutoCheckUpdate(String name, boolean autoUpdate) {
        this.name = name;
        this.autoUpdate = autoUpdate;
        updateHandlers.add(this);
    }
    public static void checkUpdates() {
        MainPage.autoUpdater.removeAll();
        MainPage.autoUpdater.add(MainPage.checkUpdatesManual);
        for (AutoCheckUpdate autoCheckUpdate : updateHandlers) {
            autoCheckUpdate.menuItem.setText(autoCheckUpdate.name+": Checking update...");
            for (ActionListener al : autoCheckUpdate.menuItem.getActionListeners()) autoCheckUpdate.menuItem.removeActionListener(al);
            MainPage.autoUpdater.add(autoCheckUpdate.menuItem);
            MainPage.autoUpdater.validate();
            checkVersion(autoCheckUpdate.name, autoCheckUpdate.autoUpdate, autoCheckUpdate.menuItem);
        }
    }
    private static void checkVersion(String name, boolean update, JMenuItem menuItem) {
        VersionInfo latestVersion = Network.getLatestVersion();
        if(latestVersion == null) {
            Network.findLatestVersion(name, () -> checkVersion(name, update, menuItem));
        }
        else {
            if (latestVersion.version.compareTo(Packages.getCurrentLatestVersion(name), true) > 0) {
                menuItem.setText(name+": New version available: "+latestVersion.releaseName);
            }
            else if (latestVersion.releaseName.length() > 0)
                menuItem.setText(name+": You have the latest version.");
            else
                menuItem.setText(name+": Could not check for update.");
            if (update) {
                menuItem.setText(name+": Installing version: "+latestVersion.releaseName+" ("+latestVersion.version.toString()+")");
                Installation.addInstall(name, latestVersion.version, latestVersion.releaseJarUrl);
                menuItem.setText(name+": Latest version installed.");
            } else {
                menuItem.addActionListener(e -> {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        if (desktop.isSupported(Desktop.Action.BROWSE)) {
                            try {
                                desktop.browse(URI.create(latestVersion.releaseUrl));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
        }    
    }
    public static void addCheckUpdate(String name, boolean autoUpdate) {
        new AutoCheckUpdate(name, autoUpdate);
    }
    public static void removeChecking(String name) {
        updateHandlers.remove(updateHandlers.stream().filter(h -> h.name.equals(name)).findAny().orElse(null));
    }
    public static void removeAllChecks() {
        updateHandlers.clear();;
    }
    public static int updatersCount() {
        return updateHandlers.size();
    }
    public static ArrayList<AutoCheckUpdate> getUpdaters() {
        return updateHandlers;
    }
    public String getName() {return name;}
    public boolean getAutoUpdate() {return autoUpdate;}
}
