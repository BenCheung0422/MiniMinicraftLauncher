package minilauncher.layout.mainLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import minilauncher.handler.Packages;
import minilauncher.layout.Layout;

public class MainPage {
    private static class packageDetails {
        public static JLabel name;
        public static JLabel version;
        public static JButton button;
    }
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
        JPanel pack = new JPanel();
        pack.setLayout(new BoxLayout(pack, BoxLayout.Y_AXIS));
        packageDetails.name = new JLabel();
        packageDetails.name.setFont(new Font("Serif", Font.PLAIN, 25));
        packageDetails.name.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageDetails.name.setHorizontalAlignment(SwingConstants.CENTER);
        packageDetails.name.setVerticalAlignment(SwingConstants.CENTER);
        pack.add(packageDetails.name);
        packageDetails.version = new JLabel();
        packageDetails.version.setFont(new Font("Serif", Font.PLAIN, 18));
        packageDetails.version.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageDetails.version.setHorizontalAlignment(SwingConstants.CENTER);
        packageDetails.version.setVerticalAlignment(SwingConstants.CENTER);
        pack.add(packageDetails.version);
        JButton startButton = new JButton();
        JSplitPane subPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, list, pack);
        subPanel.setDividerLocation(300);
        subPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subPanel);
        panel.setBackground(new Color(15, 15, 15));
        Loading.removeLayout();
        layout.getContentPane().add(panel);
        layout.getContentPane().setBackground(new Color(10, 10, 10));
        layout.validate();
    }
    private static void setPackageDetail(int index) {

    }
}
