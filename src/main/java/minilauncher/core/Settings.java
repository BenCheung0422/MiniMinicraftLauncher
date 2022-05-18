package minilauncher.core;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.handler.AutoCheckUpdate;
import minilauncher.handler.Packages;

public class Settings {
    public static boolean autoUpdateOnStart = false;
    public static void loadSettings(JSONObject jsonObject) {
        JSONArray checkUpdateOp = jsonObject.optJSONArray("AutoCheckUpdate");
        if (checkUpdateOp != null) {
            for (int i = 0; i<checkUpdateOp.length(); i++) {
                JSONObject options = checkUpdateOp.getJSONObject(i);
                AutoCheckUpdate.addCheckUpdate(options.getString("name"), options.getBoolean("update"));
            }
            autoUpdateOnStart = true;
        }
        JSONArray packCfgs = jsonObject.optJSONArray("LaunchingConfigs");
        if (packCfgs != null) {
            if (packCfgs.length() == Packages.packages.size())
                for (int i = 0; i<packCfgs.length(); i++) {
                    JSONObject cfg = packCfgs.getJSONObject(i);
                    Packages.installPackage pack = Packages.packages.get(i);
                    pack.launchingDetails.isConsole = cfg.optBoolean("console");
                    pack.launchingDetails.isDebug = cfg.optBoolean("debug");
                    pack.launchingDetails.withFabric = cfg.optBoolean("fabric");
                }
            else Log.debug("Numbers of configs and packages are not matched, the launching configs is not being loaded.");
        }
        Log.debug("All settings are loaded.");
    }
}
