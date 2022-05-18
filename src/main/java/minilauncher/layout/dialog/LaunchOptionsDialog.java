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

import minilauncher.handler.Fabric;

public class LaunchOptionsDialog extends DialogLayout {
    private JCheckBox isDebug = new JCheckBox("In debug mode");
    private JCheckBox isConsole = new JCheckBox("Show console/terminal");
    private JCheckBox withFabric = new JCheckBox("MiniFabric");
    public LaunchOptionsDialog(Frame frame, boolean modal, boolean def1, boolean def2, boolean def3) {
        super(frame, modal, new Properties());
        setTitle("Launch Option");
        isDebug.setAlignmentX(Component.LEFT_ALIGNMENT);
        isConsole.setAlignmentX(Component.LEFT_ALIGNMENT);
        withFabric.setAlignmentX(Component.LEFT_ALIGNMENT);
        isDebug.setSelected(def1);
        isConsole.setSelected(def2);
        withFabric.setSelected(def3);
        if (!Fabric.installed) withFabric.setEnabled(false);
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
        topPanel.add(withFabric);
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
        finalData = new Results(isDebug.isSelected(), isConsole.isSelected(), withFabric.isSelected());
    }
    public Results showDialog() {
        setVisible(true);
        return finalData;
    }
    public static class Results {
        public boolean isDebug;
        public boolean isConsole;
        public boolean withFabric;
        public Results(boolean val1, boolean val2, boolean val3) {
            isDebug = val1;
            isConsole = val2;
            withFabric = val3;
        }
    }
}
