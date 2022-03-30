package minilauncher.handler;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.saveload.Version;

public class Packages {
    public static void init() {}
    public static ArrayList<installPackage> packages = new ArrayList<>();
    static {
        JSONArray installations = Installation.installations.has("installations")? Installation.installations.getJSONArray("installations"): new JSONArray();
        for (int i = 0; i<installations.length(); i++) {
            JSONObject install = installations.getJSONObject(i);
            installPackage pack = new installPackage();
            pack.name = install.getString("name");
            pack.version = new Version(install.getString("version"));
            pack.gameDir = install.getString("gameDir");
            pack.saveDir = install.getString("saveDir");
            packages.add(pack);
        }
    }
    public static void addPackage(String name, Version ver, String gameDir, String saveDir) {
        installPackage pack = new installPackage();
        pack.name = name;
        pack.version = ver;
        pack.gameDir = gameDir;
        pack.saveDir = saveDir;
    }
    public static class installPackage {
        public String name;
        public Version version;
        public String gameDir;
        public String saveDir;
        public String toString() {
            return name+" "+version.toString();
        }
    }
}
