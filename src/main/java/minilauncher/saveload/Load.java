package minilauncher.saveload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.handler.Installation;

public class Load {
    public static void loadAllData() {
        JSONObject installations = new JSONObject();
        if (new File(App.dataDir+"/installations.json").exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(App.dataDir+"/installations.json"));
                String out = "";
                String line;
                while ((line = reader.readLine())!=null) out += line;
                reader.close();
                installations = new JSONObject(out);
                if (!installations.has("installations")) throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Installation.installations = installations;
    }
}
