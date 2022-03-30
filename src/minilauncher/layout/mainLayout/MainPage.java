package minilauncher.layout.mainLayout;

import java.awt.Color;

import minilauncher.layout.Layout;

public class MainPage {
    public static void loadLayout(Layout layout) {
        
        Loading.removeLayout();
        layout.setBackground(Color.BLACK);
    }
}
