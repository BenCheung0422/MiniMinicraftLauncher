package minilauncher.layout.mainLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.AbstractAction;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableModel;

import minilauncher.core.Log;
import minilauncher.handler.AutoCheckUpdate;
import minilauncher.handler.Installation;
import minilauncher.handler.Packages;
import minilauncher.layout.Layout;
import minilauncher.layout.componentlayout.ButtonColumn;
import minilauncher.layout.componentlayout.ExpandableListCom;
import minilauncher.layout.dialog.AddInstallDialog;
import minilauncher.layout.dialog.AutoCheckOptionDialog;
import minilauncher.layout.dialog.CopySaveDialog;
import minilauncher.layout.dialog.ImportInstallDialog;
import minilauncher.layout.dialog.LaunchActionsDialog;
import minilauncher.layout.dialog.LaunchOptionsDialog;
import minilauncher.layout.dialog.MoveSaveDialog;

public class MainPage {
    private static class packageCurrDetails {
        public static JLabel name;
        public static JLabel version;
        public static JButton button = new JButton("Launch");
        public static JPanel detailsPanel;
        public static JPanel infoPanel;
        public static Box infoDetailPanel;
        public static JButton launchOptionsButton = new JButton("Options");
        public static JButton launchActionsButton = new JButton("Actions");
        public static JPanel launchOptionsSPanel = new JPanel();
        public static JLabel launchOptions1 = new JLabel();
        public static JLabel launchOptions2 = new JLabel();
        public static JPanel savesPanel = new JPanel();
    }
    public static JMenu autoUpdater = new JMenu("Auto Updater");
    public static JMenuItem checkUpdatesManual = new JMenuItem("Check Updates");
    private static Layout appLayout;
    private static JPanel categoryPanels = new JPanel();
    public static void loadLayout(Layout layout) {
        JLabel label = new JLabel("MiniLauncher");
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(30, 30, 30));
        label.setBounds(0, 0, layout.getWidth(), 40);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));;
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        updatePackCategories();
        JPanel infoTitlePan = new JPanel();
        infoTitlePan.setLayout(new BoxLayout(infoTitlePan, BoxLayout.Y_AXIS));
        JPanel pack = new JPanel();
        pack.setLayout(new BorderLayout());
        packageCurrDetails.name = new JLabel();
        packageCurrDetails.name.setFont(new Font("Serif", Font.PLAIN, 25));
        packageCurrDetails.name.setForeground(Color.WHITE);
        packageCurrDetails.name.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoTitlePan.add(packageCurrDetails.name);
        packageCurrDetails.version = new JLabel();
        packageCurrDetails.version.setFont(new Font("Serif", Font.PLAIN, 18));
        packageCurrDetails.version.setForeground(Color.WHITE);
        packageCurrDetails.version.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageCurrDetails.launchOptionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageCurrDetails.launchActionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageCurrDetails.launchOptionsSPanel.setLayout(new BoxLayout(packageCurrDetails.launchOptionsSPanel, BoxLayout.Y_AXIS));
        packageCurrDetails.launchOptions1.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageCurrDetails.launchOptions2.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageCurrDetails.launchOptions1.setForeground(Color.WHITE);
        packageCurrDetails.launchOptions2.setForeground(Color.WHITE);
        packageCurrDetails.launchOptions1.setFont(new Font("Serif", Font.PLAIN, 20));
        packageCurrDetails.launchOptions2.setFont(new Font("Serif", Font.PLAIN, 20));
        packageCurrDetails.launchOptionsSPanel.add(packageCurrDetails.launchOptions1);
        packageCurrDetails.launchOptionsSPanel.add(packageCurrDetails.launchOptions2);
        packageCurrDetails.launchOptionsSPanel.setBackground(new Color(16, 16, 16));
        packageCurrDetails.savesPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        packageCurrDetails.savesPanel.setLayout(new BoxLayout(packageCurrDetails.savesPanel, BoxLayout.Y_AXIS));
        packageCurrDetails.savesPanel.setBackground(new Color(16, 16, 16));
        packageCurrDetails.savesPanel.setFont(new Font("Serif", Font.PLAIN, 20));
        packageCurrDetails.savesPanel.setForeground(Color.WHITE);
        infoTitlePan.add(packageCurrDetails.version);
        infoTitlePan.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel infoPan = new JPanel();
        infoPan.setLayout(new BoxLayout(infoPan, BoxLayout.Y_AXIS));
        infoPan.add(infoTitlePan);
        JPanel infoEmptySubPan = new JPanel();
        infoEmptySubPan.setSize(0, 60);
        infoEmptySubPan.setBackground(new Color(16, 16, 16));
        infoPan.add(infoEmptySubPan);
        Box infoDetailPan = new Box(BoxLayout.Y_AXIS);
        infoDetailPan.setBackground(new Color(16, 16, 16));
        infoPan.add(infoDetailPan);
        packageCurrDetails.infoDetailPanel = infoDetailPan;
        infoPan.setBackground(new Color(16, 16, 16));
        packageCurrDetails.infoPanel = infoPan;
        pack.add(infoPan, BorderLayout.PAGE_START);
        pack.setBackground(new Color(16, 16, 16));
        infoTitlePan.setBackground(new Color(16, 16, 16));
        categoryPanels.setLayout(new BoxLayout(categoryPanels, BoxLayout.Y_AXIS));
        categoryPanels.setBackground(new Color(25, 25, 25));
        packageCurrDetails.detailsPanel = pack;
        JScrollPane lsubPan = new JScrollPane(categoryPanels, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane rsubPan = new JScrollPane(pack, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lsubPan.setBackground(new Color(25, 25, 25));
        rsubPan.setBackground(new Color(16, 16, 16));
        JSplitPane subPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lsubPan, rsubPan);
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
        Log.debug("Removed Loading Screen");
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
        appLayout = layout;
        if (AutoCheckUpdate.updatersCount()!=0) AutoCheckUpdate.checkUpdates();
        Log.debug("Loaded MainPage Screen.");
    }
    private static void setCurrPackageDetail(int index) {
        Packages.installPackage pack = Packages.packages.get(index);
        packageCurrDetails.name.setText(pack.name);
        packageCurrDetails.version.setText(pack.version.toString());
        packageCurrDetails.detailsPanel.remove(packageCurrDetails.button);
        packageCurrDetails.button = pack.launchingDetails.launchButton;
        for (ActionListener al : packageCurrDetails.launchOptionsButton.getActionListeners()) packageCurrDetails.launchOptionsButton.removeActionListener(al);
        packageCurrDetails.launchOptionsButton.addActionListener(e -> {
            LaunchOptionsDialog.Results results = new LaunchOptionsDialog(appLayout, true).showDialog();
            if (results != null) {
                pack.launchingDetails.isDebug = results.isDebug;
                pack.launchingDetails.isConsole = results.isConsole;
                packageCurrDetails.launchOptions1.setText("Is in debug mode: "+(pack.launchingDetails.isDebug? "Yes": "No"));
                packageCurrDetails.launchOptions2.setText("Show console: "+(pack.launchingDetails.isConsole? "Yes": "No"));
            }
        });
        for (ActionListener al : packageCurrDetails.launchActionsButton.getActionListeners()) packageCurrDetails.launchActionsButton.removeActionListener(al);
        packageCurrDetails.launchActionsButton.addActionListener(e -> {
            new LaunchActionsDialog(appLayout, true, index).showDialog();
        });
        packageCurrDetails.launchOptions1.setText("Is in debug mode: "+(pack.launchingDetails.isDebug? "Yes": "No"));
        packageCurrDetails.launchOptions2.setText("Show console: "+(pack.launchingDetails.isConsole? "Yes": "No"));
        packageCurrDetails.savesPanel.removeAll();
        packageCurrDetails.savesPanel.add(new JLabel("Saves") {{
            setFont(new Font("Serif", Font.PLAIN, 20));
            setForeground(Color.WHITE);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }});
        ArrayList<Path> savesPaths;
        {
            List<Path> result = Installation.getSaves(pack.name, pack.saveDir);
            if (result != null) savesPaths = new ArrayList<>(result);
            else savesPaths = new ArrayList<>();
        }
        ArrayList<Object[]> savesList = new ArrayList<>();
        for (Path saveDir : savesPaths) {
            savesList.add(new Object[] {saveDir.getFileName(), "Copy", "Move", "Delete"});
        }
        String[] columnNames = {"Save Name", "Copy", "Move", "Delete"};
        JTable savesTable = new JTable();
        savesTable.setTableHeader(null);
        savesTable.setModel(new DefaultTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() { return savesList.size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                return savesList.get(row)[col];
            }
            public boolean isCellEditable(int row, int col) { return col>0; }
        });
        new ButtonColumn(savesTable, new AbstractAction() { // Copy
            public void actionPerformed(ActionEvent e){
                int modelRow = Integer.valueOf( e.getActionCommand() );
                Packages.installPackage pack = new CopySaveDialog(appLayout, true).showDialog();
                if (pack != null) {
                    String targetDir = Installation.getSavesDir(pack.name, pack.saveDir);
                    if (targetDir == null) JOptionPane.showMessageDialog(appLayout, "Unknown target game name.", "Error on compatibility", JOptionPane.ERROR_MESSAGE);
                    else
                        try {
                            new File(targetDir).mkdirs();
                            Files.copy(savesPaths.get(modelRow), Paths.get(targetDir+"/"+savesPaths.get(modelRow).getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
            }
        }, 1);
        new ButtonColumn(savesTable, new AbstractAction() { // Move
            public void actionPerformed(ActionEvent e){
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                Packages.installPackage pack = new MoveSaveDialog(appLayout, true).showDialog();
                if (pack != null) {
                    String targetDir = Installation.getSavesDir(pack.name, pack.saveDir);
                    if (targetDir == null) JOptionPane.showMessageDialog(appLayout, "Unknown target game name.", "Error on compatibility", JOptionPane.ERROR_MESSAGE);
                    else
                        try {
                            new File(targetDir).mkdirs();
                            Files.move(savesPaths.get(modelRow), Paths.get(targetDir+"/"+savesPaths.get(modelRow).getFileName()), StandardCopyOption.REPLACE_EXISTING);
                            ((DefaultTableModel)table.getModel()).removeRow(modelRow);
                            savesPaths.remove(modelRow);
                            savesList.remove(modelRow);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
                if (savesTable.getRowCount()==0) packageCurrDetails.savesPanel.add(new JLabel("No save") {{
                    setFont(new Font("Serif", Font.PLAIN, 16));
                    setForeground(Color.WHITE);
                    setAlignmentX(Component.CENTER_ALIGNMENT);
                }});
                packageCurrDetails.savesPanel.revalidate();
                packageCurrDetails.detailsPanel.repaint();
            }
        }, 2);
        new ButtonColumn(savesTable, new AbstractAction() { // Delete
            public void actionPerformed(ActionEvent e){
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.valueOf( e.getActionCommand() );
                try {
                    Installation.deleteDirectoryStream(savesPaths.get(modelRow));
                    ((DefaultTableModel)table.getModel()).removeRow(modelRow);
                    savesPaths.remove(modelRow);
                    savesList.remove(modelRow);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (savesTable.getRowCount()==0) packageCurrDetails.savesPanel.add(new JLabel("No save") {{
                    setFont(new Font("Serif", Font.PLAIN, 16));
                    setForeground(Color.WHITE);
                    setAlignmentX(Component.CENTER_ALIGNMENT);
                }});
                packageCurrDetails.savesPanel.revalidate();
                packageCurrDetails.detailsPanel.repaint();
            }
        }, 3);
        savesTable.setCellSelectionEnabled(false);
        savesTable.setColumnSelectionAllowed(false);
        savesTable.setRowSelectionAllowed(false);
        savesTable.setFocusable(false);
        savesTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        savesTable.setFont(new Font("Serif", Font.PLAIN, 18));
        savesTable.setForeground(Color.GRAY);
        savesTable.setBackground(new Color(40, 40, 40));
        savesTable.setRowHeight(25);
        savesTable.getColumnModel().getColumn(0).setMinWidth(400);
        savesTable.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
        packageCurrDetails.savesPanel.add(savesTable);
        if (savesTable.getRowCount()==0) packageCurrDetails.savesPanel.add(new JLabel("No save") {{
            setFont(new Font("Serif", Font.PLAIN, 16));
            setForeground(Color.WHITE);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }});
        packageCurrDetails.savesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageCurrDetails.savesPanel.revalidate();
        if (packageCurrDetails.launchActionsButton.getParent()!=packageCurrDetails.infoDetailPanel) {
            packageCurrDetails.infoDetailPanel.add(packageCurrDetails.launchOptionsButton);
            packageCurrDetails.infoDetailPanel.add(Box.createVerticalStrut(8));
            packageCurrDetails.infoDetailPanel.add(packageCurrDetails.launchActionsButton);
            packageCurrDetails.infoDetailPanel.add(Box.createVerticalStrut(10));
            packageCurrDetails.infoDetailPanel.add(packageCurrDetails.launchOptionsSPanel);
            packageCurrDetails.infoDetailPanel.add(packageCurrDetails.savesPanel);
        }
        packageCurrDetails.detailsPanel.add(packageCurrDetails.button, BorderLayout.PAGE_END);
        packageCurrDetails.detailsPanel.repaint();
    }
    private static class categoriedPackDetails {
        public final Packages.installPackage pack;
        public final int packIndex;
        public categoriedPackDetails(Packages.installPackage val1, int val2) {
            pack = val1;
            packIndex = val2;
        }
        public String toString() {
            return pack.version.toString();
        }
    }
    public static void updatePackCategories() {
        HashMap<String, ArrayList<categoriedPackDetails>> filteredPacks = new HashMap<>();
        for (int i = 0; i<Packages.packages.size(); i++) {
            Packages.installPackage pack = Packages.packages.get(i);
            String name = pack.name;
            if (!filteredPacks.containsKey(name)) {
                ArrayList<categoriedPackDetails> packs = new ArrayList<>();
                filteredPacks.put(name, packs);
                packs.add(new categoriedPackDetails(pack, i));
            } else filteredPacks.get(name).add(new categoriedPackDetails(pack, i));
        }
        ArrayList<ExpandableListCom> lists = new ArrayList<>();
        categoryPanels.removeAll();
        for (Entry<String, ArrayList<categoriedPackDetails>> packEntry : filteredPacks.entrySet()) {
            JList<String> l = new JList<>();
            l.setModel(new AbstractListModel<String>() {
                public int getSize() { return packEntry.getValue().size(); }
                public String getElementAt(int index) { return packEntry.getValue().get(index).toString(); }
            });
            l.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            ExpandableListCom listCom = new ExpandableListCom(packEntry.getKey(), l);
            l.addListSelectionListener(e -> {
                if (l.getSelectedIndex() == -1) return;
                for (ExpandableListCom com : lists) if (com.list != l) com.list.clearSelection();
                setCurrPackageDetail(packEntry.getValue().get(l.getSelectedIndex()).packIndex);
            });
            listCom.setBackground(new Color(16, 16, 16));
            listCom.setForeground(new Color(200, 200, 200));
            listCom.setFont(new Font("Serif", Font.PLAIN, 18));    
            listCom.label.setBackground(new Color(16, 16, 16));
            listCom.label.setForeground(new Color(200, 200, 200));
            listCom.label.setFont(new Font("Serif", Font.PLAIN, 18));
            l.setBackground(new Color(16, 16, 16));
            l.setForeground(new Color(200, 200, 200));
            l.setFont(new Font("Serif", Font.PLAIN, 18));    
            l.setFixedCellWidth(280);
            lists.add(listCom);
            categoryPanels.add(listCom);
        }
        categoryPanels.setBackground(new Color(16, 16, 16));
        categoryPanels.setForeground(new Color(200, 200, 200));
        categoryPanels.setFont(new Font("Serif", Font.PLAIN, 18));
        categoryPanels.repaint();
    }
}
