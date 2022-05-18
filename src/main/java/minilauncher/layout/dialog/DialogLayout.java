package minilauncher.layout.dialog;

import java.awt.Frame;
import java.awt.Window;

import javax.swing.JDialog;

public class DialogLayout extends JDialog {
    public DialogLayout(Properties properties) {
        super();
        setTitle(properties.name);
        setSize(properties.width, properties.height);
    }
    public DialogLayout(Frame frame, Properties properties) {
        super(frame);
        setTitle(properties.name);
        setSize(properties.width, properties.height);
    }
    public DialogLayout(Frame frame, boolean bool, Properties properties) {
        super(frame, bool);
        setTitle(properties.name);
        setSize(properties.width, properties.height);
    }
    public DialogLayout(Window window, Properties properties) {
        super(window);
        setTitle(properties.name);
        setSize(properties.width, properties.height);
    }
    public static class Properties {
        public String name = "New Dialog";
        public int width = 600;
        public int height = 400;
    }
}
