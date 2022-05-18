package minilauncher.handler;

import java.awt.Color;

import minilauncher.core.App;
import minilauncher.layout.mainLayout.MainPage;
import minilauncher.saveload.Version;

public class Fabric {
    public static boolean installed = false;
    public static FabircPackageInfo installPack;
    public static class FabircPackageInfo {
        public Version gameProviderVer = new Version("0");
        public Version fabricLoaderVer = new Version("0");
    }
    public static void updateFabricStatusMenu() {
        if (installed) {
            MainPage.fabricMenu.setText("Fabric - "+installPack.fabricLoaderVer+"; "+installPack.gameProviderVer);
            MainPage.fabricMenu.setForeground(Color.GREEN);
            MainPage.fabricMenu.getMenuComponent(0).setEnabled(false);
            MainPage.fabricMenu.getMenuComponent(1).setEnabled(true);
            App.layout.revalidate();
        } else {
            MainPage.fabricMenu.setText("Fabric - Not Installed");
            MainPage.fabricMenu.setForeground(Color.YELLOW);
            MainPage.fabricMenu.getMenuComponent(0).setEnabled(true);
            MainPage.fabricMenu.getMenuComponent(1).setEnabled(false);
            App.layout.revalidate();
        }
    }
}
