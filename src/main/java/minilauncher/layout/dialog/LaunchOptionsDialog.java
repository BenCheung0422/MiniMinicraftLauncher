package minilauncher.layout.dialog;

import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class LaunchOptionsDialog extends DialogLayout {
    private JCheckBox isDebug = new JCheckBox("In debug mode");
    private JCheckBox isConsole = new JCheckBox("Show console/terminal");
    public LaunchOptionsDialog(Frame frame, boolean modal) {
        super(frame, modal, new Properties());
        setTitle("Launch Option");
        isDebug.setAlignmentX(Component.LEFT_ALIGNMENT);
        isConsole.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel buttonPanel = new JPanel();
        JButton installButton = new JButton("Apply");
        installButton.addActionListener(e -> {
            getData();
            setVisible(false);
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        buttonPanel.add(installButton);
        buttonPanel.add(cancelButton);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(isDebug);
        topPanel.add(isConsole);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.LINE_END);
        add(topPanel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        LaunchOptionsDialog dialog = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }
    private Results finalData = null;
    private void getData() {
        finalData = new Results(isDebug.isSelected(), isConsole.isSelected());
    }
    public Results showDialog() {
        setVisible(true);
        return finalData;
    }
    public static class Results {
        public boolean isDebug;
        public boolean isConsole;
        public Results(boolean val1, boolean val2) {
            isDebug = val1;
            isConsole = val2;
        }
    }
}
