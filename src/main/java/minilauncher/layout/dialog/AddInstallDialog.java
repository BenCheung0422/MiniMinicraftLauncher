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

public class AddInstallDialog extends DialogLayout {
    private JComboBox<String> comboBox;
    public AddInstallDialog(Frame frame, boolean modal) {
        super(frame, modal, new Properties());
        setTitle("Add Installation");
        ArrayList<String> comboBoxSel = new ArrayList<>();
        for (Packages.installablePackage pack : Packages.installables) comboBoxSel.add(pack.name+" "+pack.version.toString());
        comboBox = new JComboBox<>(comboBoxSel.toArray(new String[0]));
        JPanel buttonPanel = new JPanel();
        JButton installButton = new JButton("Install");
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
        topPanel.add(new JLabel("Select installation"));
        topPanel.add(comboBox);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.LINE_END);
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        AddInstallDialog dialog = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }
    private Packages.installablePackage finalData = null;
    private void getData() {
        finalData = Packages.installables.get(comboBox.getSelectedIndex());
    }
    public Packages.installablePackage showDialog() {
        setVisible(true);
        return finalData;
    }
}
