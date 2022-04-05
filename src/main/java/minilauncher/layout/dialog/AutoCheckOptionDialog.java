package minilauncher.layout.dialog;

import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minilauncher.handler.AutoCheckUpdate;

public class AutoCheckOptionDialog extends DialogLayout {
    private JCheckBox optionCheck1 = new JCheckBox("Check");
    private JCheckBox optionUpdate1 = new JCheckBox("Update");
    private JCheckBox optionCheck2 = new JCheckBox("Check");
    private JCheckBox optionUpdate2 = new JCheckBox("Update");
    private JCheckBox optionCheck3 = new JCheckBox("Check");
    private JCheckBox optionUpdate3 = new JCheckBox("Update");
    public AutoCheckOptionDialog(Frame frame, boolean modal) {
        super(frame, modal, new Properties());
        setTitle("Auto Check Updater Options");
        optionCheck1.addActionListener(e -> {
            if (!optionCheck1.isSelected()) {
                optionUpdate1.setSelected(false);
                optionUpdate1.setEnabled(false);
            } else {
                optionUpdate1.setEnabled(true);
            }
        });
        optionCheck2.addActionListener(e -> {
            if (!optionCheck2.isSelected()) {
                optionUpdate2.setSelected(false);
                optionUpdate2.setEnabled(false);
            } else {
                optionUpdate2.setEnabled(true);
            }
        });
        optionCheck3.addActionListener(e -> {
            if (!optionCheck3.isSelected()) {
                optionUpdate3.setSelected(false);
                optionUpdate3.setEnabled(false);
            } else {
                optionUpdate3.setEnabled(true);
            }
        });
        JPanel optionPanel1 = new JPanel();
        optionPanel1.setLayout(new BoxLayout(optionPanel1, BoxLayout.X_AXIS));
        optionPanel1.add(new JLabel("Minicraft+: "));
        optionPanel1.add(optionCheck1);
        optionPanel1.add(optionUpdate1);
        optionPanel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel optionPanel2 = new JPanel();
        optionPanel2.setLayout(new BoxLayout(optionPanel2, BoxLayout.X_AXIS));
        optionPanel2.add(new JLabel("Aircraft: "));
        optionPanel2.add(optionCheck2);
        optionPanel2.add(optionUpdate2);
        optionPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel optionPanel3 = new JPanel();
        optionPanel3.setLayout(new BoxLayout(optionPanel3, BoxLayout.X_AXIS));
        optionPanel3.add(new JLabel("Minicraft Squared: "));
        optionPanel3.add(optionCheck3);
        optionPanel3.add(optionUpdate3);
        optionPanel3.setAlignmentX(Component.LEFT_ALIGNMENT);
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
        topPanel.add(optionPanel1);
        topPanel.add(optionPanel2);
        topPanel.add(optionPanel3);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.LINE_END);
        add(topPanel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        AutoCheckOptionDialog dialog = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }
    private String finalData = null;
    private void getData() {
        AutoCheckUpdate.removeAllChecks();
        if (optionCheck1.isSelected()) {
            AutoCheckUpdate.addCheckUpdate("Minicraft+", optionUpdate1.isSelected());
        }
        if (optionCheck2.isSelected()) {
            AutoCheckUpdate.addCheckUpdate("Aircraft", optionUpdate2.isSelected());
        }
        if (optionCheck3.isSelected()) {
            AutoCheckUpdate.addCheckUpdate("Minicraft Square", optionUpdate3.isSelected());
        }
        AutoCheckUpdate.checkUpdates();
    }
    public String showDialog() {
        setVisible(true);
        return finalData;
    }
}
