package minilauncher.layout.mainLayout;

import javax.swing.JLabel;

import minilauncher.layout.Layout;

public class Loading {
    private static JLabel label;
    private static Layout layout;
    public static void loadLayout(Layout layout) {
        JLabel label = new JLabel("<h2>Loading...</h2>");
        Loading.label = label;
        layout.add(label, JLabel.CENTER);
        Loading.layout = layout;
    }
    public static void removeLayout() {
        layout.remove(label);
    }
}
