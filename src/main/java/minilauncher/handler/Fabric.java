package minilauncher.handler;

import minilauncher.saveload.Version;

public class Fabric {
    public static boolean installed = false;
    public static FabircPackageInfo installPack;
    public static class FabircPackageInfo {
        public Version gameProviderVer = new Version("0");
        public Version fabricLoaderVer = new Version("0");
    }
    
}
