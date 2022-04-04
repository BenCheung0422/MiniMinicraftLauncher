package minilauncher.handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.core.Log;
import minilauncher.core.Network;
import minilauncher.saveload.Version;

public class Installation {
    public static JSONObject installations;
    public static JSONArray installables;
    public static void importInstall(String jarDir, String gameDataDir, boolean separateData, String gameName, String gameVersion) {
        if (!new File(jarDir).exists()||!jarDir.endsWith(".jar")) {
            Log.debug("The jar file is unexist or not jar file, returning...");
            return;
        }
        if (!Version.isValid(gameVersion)) {
            Log.debug("Invalid game version: "+gameVersion+", using 1.0.0 instead.");
            gameVersion = "1.0.0";
        }
        String gameDir = App.dataDir+"/installations/"+gameName+"/"+gameVersion;
        new File(gameDir).mkdirs();
        try {
            Files.copy(Paths.get(jarDir), Paths.get(gameDir+"/game.jar"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (gameDataDir == null) gameDataDir = gameDir+"/data/";
        if (separateData) {
            if (new File(gameDataDir).exists()) {
                try {
                    String sourceString = gameDataDir;
                    Files.walk(Paths.get(sourceString))
                        .forEach(source -> {
                            Path destination = Paths.get(gameDir+"/data/", source.toString()
                                .substring(sourceString.length()));
                            try {
                                Files.copy(source, destination);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            gameDataDir = gameDir+"/data/";
            new File(gameDataDir).mkdirs();
        }
        Packages.addPackage(gameName, new Version(gameVersion), gameDir+"/game.jar", gameDataDir);
    }
    public static void addInstall(String gameName, Version gameVersion, String gameDownloadURL) {
        String gameDir = App.dataDir+"/installations/"+gameName+"/"+gameVersion.toString();
        new File(gameDir).mkdirs();
        try {
            Network.downloadFile(new URL(gameDownloadURL), gameDir+"/game.jar");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        new File(gameDir+"/data/").mkdirs();
        Packages.addPackage(gameName, gameVersion, gameDir+"/game.jar", gameDir+"/data/");
    }
}
