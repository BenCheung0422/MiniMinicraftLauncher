package minilauncher.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.layout.mainLayout.MainPage;
import minilauncher.saveload.Save;
import minilauncher.saveload.Version;

public class Packages {
    public static void init() {}
    public static ArrayList<installPackage> packages = new ArrayList<>();
    public static ArrayList<installablePackage> installables = new ArrayList<>();
    static {
        JSONArray installations = Installation.installations.has("installations")? Installation.installations.getJSONArray("installations"): new JSONArray();
        for (int i = 0; i<installations.length(); i++) {
            JSONObject install = installations.getJSONObject(i);
            installPackage pack = new installPackage();
            pack.name = install.getString("name");
            pack.version = new Version(install.getString("version"));
            pack.gameDir = install.getString("gameDir");
            pack.saveDir = install.getString("saveDir");
            pack.launchingDetails = LaunchingDetails.getDefaultDetails(pack);
            packages.add(pack);
        }
        JSONArray installables = Installation.installables;
        for (int i = 0; i<installables.length(); i++) {
            JSONObject game = installables.getJSONObject(i);
            JSONArray installs = game.getJSONArray("installs");
            String gameName = game.getString("name");
            for (int j = 0; j<installs.length(); j++) {
                installablePackage pack = new installablePackage();
                pack.name = gameName;
                JSONObject p = installs.getJSONObject(j);
                pack.version = new Version(p.getString("version"));
                pack.gameDownloadURL = p.getString("gameURL");
                Packages.installables.add(pack);
            }
        }
    }
    public static void addPackage(String name, Version ver, String gameDir, String saveDir) {
        installPackage pack = new installPackage();
        pack.name = name;
        pack.version = ver;
        pack.gameDir = gameDir;
        pack.saveDir = saveDir;
        pack.launchingDetails = LaunchingDetails.getDefaultDetails(pack);
        packages.add(pack);
        MainPage.validateLeftListBar();
        Save.savePackageList();
    }
    public static installPackage getCurrentLatest(String name) {
        installPackage latest = new installPackage();
        latest.version = new Version("0");
        for (installPackage pack : packages) {
            if (pack.name == name) {
                if (latest.version.compareTo(pack.version)<0) latest = pack;
            }
        }
        if (latest.version.compareTo(new Version("0"))==0) return null;
        return latest;
    }
    public static Version getCurrentLatestVersion(String name) {
        Version latest = new Version("0");
        for (installPackage pack : packages) {
            if (pack.name == name) {
                if (latest.compareTo(pack.version)<0) latest = pack.version;
            }
        }
        return latest;
    }
    public static class installPackage {
        public String name;
        public Version version;
        public String gameDir;
        public String saveDir;
        public LaunchingDetails launchingDetails;
        public String toString() {
            return name+" "+version.toString();
        }
        public boolean equals(installPackage pack) {
            return this.name.equals(pack.name) &&
            this.version.equals(pack.version) &&
            this.gameDir.equals(pack.gameDir) &&
            this.saveDir.equals(pack.saveDir);
        }
    }
    public static class installablePackage {
        public String name;
        public Version version;
        public String gameDownloadURL;
    }
}
