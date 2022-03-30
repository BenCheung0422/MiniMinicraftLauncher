package minilauncher.layout.mainLayout;

import java.awt.Font;

import javax.swing.JLabel;

import minilauncher.layout.Layout;

public class Loading {
    private static JLabel label;
    private static Layout layout;
    public static void loadLayout(Layout layout) {
        JLabel label = new JLabel("Loading...");
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        Loading.label = label;
        layout.add(label, JLabel.CENTER);
        Loading.layout = layout;
    }
    public static void removeLayout() {
        layout.remove(label);
    }
}
