package minilauncher.layout.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import minilauncher.handler.Packages;

public class MoveSaveDialog extends DialogLayout {
    private JComboBox<String> comboBox;
    public MoveSaveDialog(Frame frame, boolean modal) {
        super(frame, modal, new Properties());
        setTitle("Move Save");
        ArrayList<String> comboBoxSel = new ArrayList<>();
        for (Packages.installPackage pack : Packages.packages) comboBoxSel.add(pack.toString());
        comboBox = new JComboBox<>(comboBoxSel.toArray(new String[0]));
        JPanel buttonPanel = new JPanel();
        JButton importButton = new JButton("Move");
        importButton.addActionListener(e -> {
            getData();
            setVisible(false);
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        buttonPanel.add(importButton);
        buttonPanel.add(cancelButton);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(new JLabel("Move to"));
        topPanel.add(comboBox);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.LINE_END);
        add(topPanel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        MoveSaveDialog dialog = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }
    private Packages.installPackage finalData = null;
    private void getData() {
        finalData = Packages.packages.get(comboBox.getSelectedIndex());
    }
    public Packages.installPackage showDialog() {
        setVisible(true);
        return finalData;
    }
}
