package minilauncher.layout.mainLayout;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import minilauncher.layout.Layout;

public class Loading {
    private static JLabel label;
    private static Layout layout;
    public static void loadLayout(Layout layout) {
        JLabel label = new JLabel("Loading...");
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        Loading.label = label;
        layout.getContentPane().add(label, BorderLayout.CENTER);
        Loading.layout = layout;
    }
    public static void removeLayout() {
        layout.getContentPane().remove(label);
    }
}
