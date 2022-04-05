package minilauncher.layout.mainLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import minilauncher.core.Log;
import minilauncher.handler.AutoCheckUpdate;
import minilauncher.handler.Installation;
import minilauncher.handler.Launcher;
import minilauncher.handler.Packages;
import minilauncher.layout.Layout;
import minilauncher.layout.dialog.AddInstallDialog;
import minilauncher.layout.dialog.AutoCheckOptionDialog;
import minilauncher.layout.dialog.ImportInstallDialog;

public class MainPage {
    private static class packageDetails {
        public static JLabel name;
        public static JLabel version;
        public static JButton button = new JButton("Launch");
        public static JPanel detailsPanel;
        public static JPanel infoPanel;
    }
    public static JMenu autoUpdater = new JMenu("Auto Updater");
    public static JMenuItem checkUpdatesManual = new JMenuItem("Check Updates");
    public static void loadLayout(Layout layout) {
        JLabel label = new JLabel("MiniLauncher");
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(20, 20, 20));
        label.setBounds(0, 0, layout.getWidth(), 40);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));;
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        JList<String> list = new JList<String>();
        list.setModel(new AbstractListModel<String>() {
            public int getSize() { return Packages.packages.size(); }
            public String getElementAt(int index) { return Packages.packages.get(index).toString(); }
        });
        list.addListSelectionListener(e -> {
            setPackageDetail(list.getSelectedIndex());
        });
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setBackground(new Color(16, 16, 16));
        list.setForeground(new Color(200, 200, 200));
        list.setFont(new Font("Serif", Font.PLAIN, 18));
        list.setFixedCellWidth(300);
        list.setSelectionForeground(new Color(180, 180, 180));
        list.setSelectionBackground(new Color(35, 35, 35));
        JPanel infoPan = new JPanel();
        infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.Y_AXIS));
        JPanel pack = new JPanel();
        pack.setLayout(new BorderLayout());
        packageDetails.name = new JLabel();
        packageDetails.name.setFont(new Font("Serif", Font.PLAIN, 25));
        packageDetails.name.setForeground(Color.WHITE);
        packageDetails.name.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageDetails.name.setHorizontalAlignment(SwingConstants.CENTER);
        packageDetails.name.setVerticalAlignment(SwingConstants.CENTER);
        infoPan.add(packageDetails.name);
        packageDetails.version = new JLabel();
        packageDetails.version.setFont(new Font("Serif", Font.PLAIN, 18));
        packageDetails.version.setForeground(Color.WHITE);
        packageDetails.version.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageDetails.version.setHorizontalAlignment(SwingConstants.CENTER);
        packageDetails.version.setVerticalAlignment(SwingConstants.CENTER);
        infoPan.add(packageDetails.version);
        pack.add(infoPan, BorderLayout.PAGE_START);
        pack.setBackground(new Color(16, 16, 16));
        infoPan.setBackground(new Color(16, 16, 16));
        packageDetails.button.setForeground(Color.BLACK);
        packageDetails.button.setBackground(Color.GREEN);
        packageDetails.detailsPanel = pack;
        JSplitPane subPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, pack);
        subPanel.setDividerLocation(300);
        subPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPanel.setEnabled(false);
        subPanel.setBackground(new Color(20, 20, 20));
        subPanel.setDividerSize(3);
        subPanel.setUI(new BasicSplitPaneUI() 
        {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() 
            {
                return new BasicSplitPaneDivider(this) 
                {                
                    public void setBorder(Border b) {}
        
                    @Override
                    public void paint(Graphics g) 
                    {
                        g.setColor(new Color(30, 30, 30));
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });
        subPanel.setBorder(null);
        subPanel.setForeground(new Color(20, 20, 20));
        panel.add(subPanel);
        panel.setBackground(new Color(15, 15, 15));
        Loading.removeLayout();
        layout.getContentPane().add(panel);
        layout.getContentPane().setBackground(new Color(10, 10, 10));
        JMenuBar menuBar = layout.getJMenuBar();
        JMenu optionsMenu = menuBar.getMenu(0);
        JMenuItem updateOptionsMenu = new JMenuItem("Auto Check Updater Options");
        updateOptionsMenu.addActionListener(e -> {
            new AutoCheckOptionDialog(layout, true).showDialog();
        });
        updateOptionsMenu.setMnemonic(KeyEvent.VK_U);
        optionsMenu.add(updateOptionsMenu, 0);
        autoUpdater.setMnemonic(KeyEvent.VK_A);
        checkUpdatesManual.addActionListener(e -> {
            AutoCheckUpdate.checkUpdates();
        });
        autoUpdater.add(checkUpdatesManual);
        menuBar.add(autoUpdater);
        JMenu installMenu = new JMenu("Installations");
        JMenuItem importInstallMenu = new JMenuItem("Import installation");
        importInstallMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        importInstallMenu.addActionListener(e -> {
            ImportInstallDialog.Results result = new ImportInstallDialog(layout, true).showDialog();
            if (result != null) {
                Installation.importInstall(result.dir1, result.dir2, result.separateData, result.Name, result.VersionString);;
            }
        });
        importInstallMenu.setMnemonic(KeyEvent.VK_I);
        JMenuItem addInstallMenu = new JMenuItem("Add installation");
        addInstallMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        addInstallMenu.addActionListener(e -> {
            Packages.installablePackage result = new AddInstallDialog(layout, true).showDialog();
            if (result != null) {
                Installation.addInstall(result.name, result.version, result.gameDownloadURL);
            }
        });
        addInstallMenu.setMnemonic(KeyEvent.VK_A);
        installMenu.add(importInstallMenu);
        installMenu.add(addInstallMenu);
        installMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        installMenu.setMnemonic(KeyEvent.VK_I);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(installMenu);
        layout.validate();
        if (AutoCheckUpdate.updatersCount()!=0) AutoCheckUpdate.checkUpdates();
    }
    private static void setPackageDetail(int index) {
        Packages.installPackage pack = Packages.packages.get(index);
        packageDetails.name.setText(pack.name);
        packageDetails.version.setText(pack.version.toString());
        packageDetails.detailsPanel.remove(packageDetails.button);
        for (ActionListener al : packageDetails.button.getActionListeners()) packageDetails.button.removeActionListener(al);
        packageDetails.button.addActionListener(e -> {
            Log.debug("Launching game...");
            Launcher.launchGame(pack, packageDetails.button);
        });
        packageDetails.detailsPanel.add(packageDetails.button, BorderLayout.PAGE_END);
    }
}
