package minilauncher.saveload;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.core.Log;
import minilauncher.handler.Packages;

public class Save {
    public static void savePackageList() {
        JSONObject json = new JSONObject();
        JSONArray jsonarray = new JSONArray();
        for (Packages.installPackage pack : Packages.packages) {
            JSONObject packJson = new JSONObject();
            packJson.put("name", pack.name);
            packJson.put("version", pack.version);
            packJson.put("gameDir", pack.gameDir);
            packJson.put("saveDir", pack.saveDir);
            jsonarray.put(packJson);
        }
        json.put("installations", jsonarray);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(App.dataDir+"/installations.json"));
            bufferedWriter.write(json.toString(4));
            bufferedWriter.close();
            Log.debug("Saved Package List.");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void saveInstallables() {saveInstallables(false);}
    public static void saveInstallables(boolean defaultFile) {
        if (defaultFile) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(App.dataDir+"/installables.json"));
                bufferedWriter.write(new String(App.class.getResourceAsStream("/resources/installables.json").readAllBytes()));
                bufferedWriter.close();
                Log.debug("Saved default installables.json.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
