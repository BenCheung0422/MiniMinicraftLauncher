package minilauncher.handler;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import minilauncher.core.App;
import minilauncher.core.Log;
import minilauncher.core.Network;
import minilauncher.layout.mainLayout.MainPage;
import minilauncher.saveload.Save;
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
        Log.debug("Installation added.");
        StatusBar.setStatus("New installation added");
        StatusBar.visible(3000);
    }

    public static void removeInstall(int packageIndex, boolean keepData) {
        Packages.installPackage pack = Packages.packages.get(packageIndex);
        try {
            Files.delete(Paths.get(pack.gameDir));
            if (!keepData) deleteDirectoryStream(Paths.get(pack.saveDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Packages.removePackage(packageIndex);
        Log.debug("Installation removed.");
        StatusBar.setStatus("Installation removed");
        StatusBar.visible(3000);
    }

    public static void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }

    public static List<Path> getSaves(String name, String saveDir) {
        String savesDir = getSavesDir(name, saveDir);
        if (savesDir == null) return null;
        if (!new File(savesDir).exists()) return null;
        return Arrays.stream(new File(savesDir).list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
              return new File(current, name).isDirectory();
            }
        })).map(s -> Paths.get(savesDir+"/"+s)).collect(Collectors.toList());
    }

    public static String getSavesDir(String name, String saveDir) {
        if (name.equals("Minicraft+")) return saveDir+"/playminicraft/mods/Minicraft_Plus/saves";
        else if (name.equals("Aircraft")) return saveDir+"/playminicraft/mods/Aircraft/saves";
        else if (name.equals("Minicraft Squared")) return saveDir+"/playminicraft/mods/Minicraft_2/saves";
        else return null;
    }

    public static void installFabric() {
        if (Fabric.installed) {
            Log.debug("Fabric has already been installed; Returning...");
            return;
        }
        new File(App.dataDir+"/fabric").mkdirs();
        Log.debug("Installing Fabric...");
        StatusBar.setStatus("Installing Fabric...");
        StatusBar.visible(4000);
        try {
            ZipInputStream zis = new ZipInputStream(new URL("https://raw.githubusercontent.com/BenCheung0422/MiniMinicraftLauncher/main/fabric.zip").openStream());
            // list files in zip
            ZipEntry zipEntry = zis.getNextEntry();
            Path target = Paths.get(App.dataDir+"/fabric/");
            while (zipEntry != null) {
                Path newPath = zipSlipProtect(zipEntry, target);
                Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            Fabric.installPack = new Fabric.FabircPackageInfo();
            Fabric.installPack.gameProviderVer= new Version("0.13.3");
            Fabric.installPack.fabricLoaderVer = new Version("1.1.1");
            Fabric.installed = true;
            Log.debug("Fabric installed.");
            StatusBar.setStatus("Fabric installed.");
            StatusBar.visible(4000);
            Save.savePackageList();
            Fabric.updateFabricStatusMenu();
        } catch (IOException e) {
            e.printStackTrace();
            Log.debug("Error on installing Fabric.");
            StatusBar.setStatus("Error on installing Fabric.");
            StatusBar.visible(4000);
    }
    }

    public static void removeFabric() {
        if (!Fabric.installed) return;
        File[] files = new File(App.dataDir+"/fabric").listFiles();
        for (File f : files) f.delete();
        Fabric.installPack = null;
        Fabric.installed = false;
        Save.savePackageList();
        Fabric.updateFabricStatusMenu();
        Log.debug("Fabric removed.");
        StatusBar.setStatus("Fabric uninstalled.");
        StatusBar.visible(3000);
    }

    // protect zip slip attack
    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
        throws IOException {

        // test zip slip vulnerability
        // Path targetDirResolved = targetDir.resolve("../../" + zipEntry.getName());

        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        // make sure normalized file still has targetDir as its prefix
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }

        return normalizePath;
    }
}
