package minilauncher.saveload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.core.Settings;
import minilauncher.handler.Installation;

public class Load {
    public static void loadAllData() {
        JSONObject installations = new JSONObject();
        if (new File(App.dataDir+"/installations.json").exists()) {
            try {
                installations = new JSONObject(loadFile(App.dataDir+"/installations.json"));
                if (!installations.has("installations")) throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Installation.installations = installations;
        loadInstallables();
        loadSettings();
    }
    public static void loadInstallables() {
        if (new File(App.dataDir+"/installables.json").exists()) {
            JSONArray installables = new JSONArray();
            try {
                installables = new JSONArray(loadFile(App.dataDir+"/installables.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Installation.installables = installables;
        } else {
            Save.saveInstallables(true);
            loadInstallables();
        }
    }
    public static void loadSettings() {
        JSONObject settings = new JSONObject();
        if (new File(App.dataDir+"/settings.json").exists()) {
            try {
                settings = new JSONObject(loadFile(App.dataDir+"/settings.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Settings.loadSettings(settings);
    }
    public static String loadFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String out = "";
        String line;
        while ((line = reader.readLine())!=null) out += line;
        reader.close();
        return out;
    }
}
