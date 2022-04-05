package minilauncher.core;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.handler.AutoCheckUpdate;

public class Settings {
    public static void loadSettings(JSONObject jsonObject) {
        JSONArray checkUpdateOp = jsonObject.optJSONArray("AutoCheckUpdate");
        if (checkUpdateOp != null) {
            for (int i = 0; i<checkUpdateOp.length(); i++) {
                JSONObject options = checkUpdateOp.getJSONObject(i);
                AutoCheckUpdate.addCheckUpdate(options.getString("name"), options.getBoolean("update"));
            }
        }
    }
}
