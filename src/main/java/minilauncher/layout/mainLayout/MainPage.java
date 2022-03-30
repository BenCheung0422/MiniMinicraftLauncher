package minilauncher.layout.mainLayout;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import minilauncher.layout.Layout;

public class MainPage {
    public static void loadLayout(Layout layout) {
        JLabel label = new JLabel("MiniLauncher");
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(20, 20, 20));
        label.setBounds(0, 0, layout.getWidth(), 40);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));;
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        JPanel panel = new JPanel();
        panel.add(label);
        JList<String> list = new JList<String>();
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JSplitPane subPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        subPanel.setDividerLocation(300);
        panel.add(subPanel);
        Loading.removeLayout();
        layout.getContentPane().setBackground(new Color(10, 10, 10));
        layout.add(panel);
    }
}
