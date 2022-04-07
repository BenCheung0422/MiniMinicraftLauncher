package minilauncher.layout.componentlayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExpandableListCom extends JPanel {
    private boolean isExpanded = false;
    public JLabel label;
    public JList<String> list;
    public ExpandableListCom(String title, JList<String> list) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel(title);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.list = list;
        list.setAlignmentX(Component.LEFT_ALIGNMENT);
        list.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isExpanded) {
                    remove(list);
                    revalidate();
                    isExpanded = false;
                } else {
                    add(list);
                    revalidate();
                    isExpanded = true;
                }
            }
        });
        add(label);
    }
}
