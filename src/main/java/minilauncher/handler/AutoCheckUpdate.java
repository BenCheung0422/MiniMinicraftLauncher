package minilauncher.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JMenuItem;

import minilauncher.core.Log;
import minilauncher.core.Network;
import minilauncher.core.VersionInfo;
import minilauncher.layout.mainLayout.MainPage;

public class AutoCheckUpdate {
    private static ArrayList<AutoCheckUpdate> updateHandlers = new ArrayList<>();
    private String name;
    private boolean autoUpdate;
    private JMenuItem menuItem = new JMenuItem();
    private static HashMap<String, String> statusBarStatus = new HashMap<>();

    private AutoCheckUpdate(String name, boolean autoUpdate) {
        this.name = name;
        this.autoUpdate = autoUpdate;
        updateHandlers.add(this);
    }

    public static void checkUpdates() {
        Log.debug("Checking updates...");
        MainPage.autoUpdater.removeAll();
        MainPage.autoUpdater.add(MainPage.checkUpdatesManual);
        MainPage.autoUpdater.revalidate();
        statusBarStatus.clear();
        if (updateHandlers.size() == 0) {
            StatusBar.setStatus("No update checking is needed: Auto update is not selected.");
            StatusBar.visible(3000);    
        }
        for (AutoCheckUpdate autoCheckUpdate : updateHandlers) {
            statusBarStatus.put(autoCheckUpdate.name, autoCheckUpdate.name+": Checking update...");
            updateStatusBar();
            for (ActionListener al : autoCheckUpdate.menuItem.getActionListeners()) autoCheckUpdate.menuItem.removeActionListener(al);
            checkVersion(autoCheckUpdate.name, autoCheckUpdate.autoUpdate, autoCheckUpdate.menuItem);
        }
    }
    
    private static void updateStatusBar() {
        StatusBar.setStatus(String.join("\n", statusBarStatus.values()));
        StatusBar.visible(5000);
    }
    private static void addUpdaterMenuItem(JMenuItem item) {
        MainPage.autoUpdater.add(item);
        MainPage.autoUpdater.revalidate();
    }

    private static void checkVersion(String name, boolean update, JMenuItem menuItem) {
        VersionInfo latestVersion = Network.getLatestVersion();
        if(latestVersion == null) {
            Network.findLatestVersion(name, () -> checkVersion(name, update, menuItem));
        }
        else {
            if (latestVersion.version.compareTo(Packages.getCurrentLatestVersion(name), true) > 0) {
                statusBarStatus.put(name, name+": New version available: "+latestVersion.releaseName);
                updateStatusBar();
            } else if (latestVersion.releaseName.length() > 0) {
                statusBarStatus.put(name, name+": You have the latest version.");
                updateStatusBar();
                menuItem.setText(name+" - Latest");
                menuItem.setEnabled(false);
                addUpdaterMenuItem(menuItem);
                return;
            } else {
                statusBarStatus.put(name, name+": Could not check for update.");
                updateStatusBar();
                menuItem.setText(name+" - Unable to check");
                menuItem.setEnabled(false);
                addUpdaterMenuItem(menuItem);
                return;
            }
            if (update) {
                menuItem.setEnabled(false);
                statusBarStatus.put(name, name+": Installing version: "+latestVersion.releaseName+" ("+latestVersion.version.toString()+")");
                Installation.addInstall(name, latestVersion.version, latestVersion.releaseJarUrl);
                statusBarStatus.put(name, name+": Installed the latest version.");
                menuItem.setText(name+": Installed Latest.");
                addUpdaterMenuItem(menuItem);
            } else {
                menuItem.setEnabled(true);
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
                menuItem.setText(name+" - Available latest: "+latestVersion.releaseName);
                addUpdaterMenuItem(menuItem);
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
