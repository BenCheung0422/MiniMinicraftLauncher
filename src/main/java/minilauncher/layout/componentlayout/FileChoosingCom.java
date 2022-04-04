package minilauncher.layout.componentlayout;

import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileChoosingCom extends JPanel {
    private JTextField fileChoosed = new JTextField();
    private JButton fileChooseButton = new JButton("Dir");
    private JDialog parent;
    private JFileChooser fc;
    public FileChoosingCom(JDialog parent) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.parent = parent;
    }
    public JTextField addFileTextField(String defaultDir) {
        add(fileChoosed);
        fileChoosed.setMargin(new Insets(5, 5, 5, 3));
        fileChoosed.setText(defaultDir);
        return fileChoosed;
    }
    public JButton addFileButton() {
        add(fileChooseButton);
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooseButton.setMargin(new Insets(5, 2, 5, 5));
        fileChooseButton.addActionListener(e -> {
            int val = fc.showOpenDialog(parent);
            if (val == JFileChooser.APPROVE_OPTION) {
                fileChoosed.setText(fc.getSelectedFile().getPath());
            }
        });
        return fileChooseButton;
    }
    public JFileChooser getFileChooser() {return fc;}
}
