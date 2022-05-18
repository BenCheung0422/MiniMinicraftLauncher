package minilauncher.saveload;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.core.Log;
import minilauncher.handler.AutoCheckUpdate;
import minilauncher.handler.Fabric;
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
        JSONObject fabricObj = new JSONObject();
        fabricObj.put("installed", Fabric.installed);
        if (Fabric.installed) {
            fabricObj.put("providerVersion", Fabric.installPack.gameProviderVer);
            fabricObj.put("loaderVersion", Fabric.installPack.fabricLoaderVer);
        }
        json.put("fabric", fabricObj);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(App.dataDir+"/installations.json"));
            bufferedWriter.write(json.toString(4));
            bufferedWriter.close();
            Log.debug("Saved Package List.");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        saveSettings();
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
    public static void saveSettings() {
        JSONObject settings = new JSONObject();
        if (AutoCheckUpdate.updatersCount() != 0) {
            JSONArray updatersOp = new JSONArray();
            for (AutoCheckUpdate update : AutoCheckUpdate.getUpdaters()) {
                JSONObject options = new JSONObject();
                options.put("name", update.getName());
                options.put("update", update.getAutoUpdate());
                updatersOp.put(options);
            }
            settings.put("AutoCheckUpdate", updatersOp);
        }
        JSONArray packsCfg = new JSONArray();
        for (Packages.installPackage pack : Packages.packages) {
            JSONObject cfg = new JSONObject();
            cfg.put("console", pack.launchingDetails.isConsole);
            cfg.put("debug", pack.launchingDetails.isDebug);
            cfg.put("fabric", pack.launchingDetails.withFabric);
            packsCfg.put(cfg);
        }
        settings.put("LaunchingConfigs", packsCfg);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(App.dataDir+"/settings.json"))) {
			bufferedWriter.write(settings.toString(4));
            Log.debug("Saved settings.json.");
        } catch (IOException e) {
            e.printStackTrace();
            Log.debug("Failed to save settings.json.");
        }
    }
}
