package minilauncher.core;

import java.io.File;

import minilauncher.saveload.Load;

public class FileHandler {
    public static void makeDirs() {
        new File(App.dataDir).mkdirs();
        Load.loadAllData();
    }
}
