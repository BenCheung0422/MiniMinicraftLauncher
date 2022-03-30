package minilauncher.layout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Layout extends JFrame {
    public Layout(Properties properties) {
        setTitle(properties.name);
        setSize(properties.width, properties.height);
        if (properties.menuBar != null) setJMenuBar(properties.menuBar);
    }
    public static class Properties {
        public String name = "MiniLauncher";
        public int width = 1000;
        public int height = 600;
        public JMenuBar menuBar = null;
    }
}
