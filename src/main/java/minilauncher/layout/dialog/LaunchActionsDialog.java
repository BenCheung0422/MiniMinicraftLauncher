package minilauncher.layout.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import minilauncher.handler.Installation;
import minilauncher.handler.Packages;

public class LaunchActionsDialog extends DialogLayout {
    public LaunchActionsDialog(Frame frame, boolean modal, int packIndex) {
        super(frame, modal, new Properties());
        setTitle("Launch Actions");
        Packages.installPackage pack = Packages.packages.get(packIndex);
        JButton uninstallButton = new JButton("Uninstall game");
        uninstallButton.addActionListener(e -> {
            int r = JOptionPane.showOptionDialog(this, "Keep game data?", "Game uninstalling", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, JOptionPane.CANCEL_OPTION);
            if (r == JOptionPane.CANCEL_OPTION || r == JOptionPane.CLOSED_OPTION) return;
            else if (r == JOptionPane.YES_OPTION) {
                r = JOptionPane.showConfirmDialog(this, "The game will be uninstalled without game data..", "Game uninstallation confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (r == JOptionPane.CANCEL_OPTION || r == JOptionPane.CLOSED_OPTION) return;
                Installation.removeInstall(packIndex, true);
                setVisible(false);
                dispose();
            } else {
                r = JOptionPane.showConfirmDialog(this, "The game will be removed with game data.", "Game uninstallation confirm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (r == JOptionPane.CANCEL_OPTION || r == JOptionPane.CLOSED_OPTION) return;
                Installation.removeInstall(packIndex, false);
                setVisible(false);
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        buttonPanel.add(closeButton);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(uninstallButton);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.LINE_END);
        add(topPanel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        LaunchActionsDialog dialog = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }
    public void showDialog() {
        setVisible(true);
    }
}
