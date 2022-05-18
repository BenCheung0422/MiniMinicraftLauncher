package minilauncher.layout.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import minilauncher.core.App;
import minilauncher.layout.componentlayout.FileChoosingCom;

public class ImportInstallDialog extends DialogLayout {
    private JCheckBox separateGameDataCheck = new JCheckBox("Separate game data from origin");
    private FileChoosingCom fileChoosingCom1 = new FileChoosingCom(this);
    private FileChoosingCom fileChoosingCom2 = new FileChoosingCom(this);
    private JTextField fileChoosedField1;
    private JTextField fileChoosedField2;   
    private JTextField extraInput1 = new JTextField(); 
    private JTextField extraInput2 = new JTextField(); 
    public ImportInstallDialog(Frame frame, boolean modal) {
        super(frame, modal, new Properties());
        setTitle("Import Installation");
        separateGameDataCheck.setSelected(true);
        separateGameDataCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel fileChoose1 = new JPanel();
        fileChoose1.add(new JLabel("Game File (.jar): "));
        fileChoosedField1 = fileChoosingCom1.addFileTextField(App.dataDir);
        fileChoosingCom1.addFileButton();
        fileChoosingCom1.getFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);;
        fileChoosingCom1.getFileChooser().setFileFilter(new FileNameExtensionFilter("JAR File", "jar"));
        fileChoose1.add(fileChoosingCom1);
        fileChoose1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel fileChoose2 = new JPanel();
        fileChoose2.add(new JLabel("Game Data Folder: "));
        fileChoosedField2 = fileChoosingCom2.addFileTextField(App.dataDir);
        fileChoosedField2.setToolTipText("Enter \"DEFAULT\" if use default data path.");
        fileChoosingCom2.addFileButton();
        fileChoose2.add(fileChoosingCom2);
        fileChoose2.setAlignmentX(Component.LEFT_ALIGNMENT);
        fileChoosedField1.setColumns(30);
        fileChoosedField2.setColumns(30);
        fileChoosedField1.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {}
            public void removeUpdate(DocumentEvent e) {}
            public void changedUpdate(DocumentEvent e) {
                fileChoosedField2.setText(App.dataDir+"/installations/"+FilenameUtils.removeExtension(new File(fileChoosedField1.getText()).getName())+"/ver/data/");
            }
        });
        JPanel extraInfoPanel = new JPanel();
        JPanel extraInputPanel1 = new JPanel();
        extraInputPanel1.add(new JLabel("Game Name: "));
        extraInput1.setColumns(12);
        extraInputPanel1.add(extraInput1);
        extraInputPanel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel extraInputPanel2 = new JPanel();
        extraInputPanel2.add(new JLabel("Game Version: "));
        extraInput2.setColumns(8);
        extraInputPanel2.add(extraInput2);
        extraInputPanel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        extraInfoPanel.setLayout(new BoxLayout(extraInfoPanel, BoxLayout.Y_AXIS));
        extraInfoPanel.add(extraInputPanel1);
        extraInfoPanel.add(extraInputPanel2);
        extraInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel buttonPanel = new JPanel();
        JButton importButton = new JButton("Import");
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
        topPanel.add(fileChoose1);
        topPanel.add(fileChoose2);
        topPanel.add(separateGameDataCheck);
        topPanel.add(extraInfoPanel);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.LINE_END);
        add(topPanel, BorderLayout.PAGE_START);
        add(bottomPanel, BorderLayout.PAGE_END);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        ImportInstallDialog dialog = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dispose();
            }
        });
        setLocationRelativeTo(null);
    }
    private Results finalData = null;
    private void getData() {
        String dataPath = fileChoosedField2.getText();
        if (dataPath=="DEFAULT") dataPath = null;
        finalData = new Results(fileChoosedField1.getText(), dataPath, separateGameDataCheck.isSelected(), extraInput1.getText(), extraInput2.getText());
    }
    public Results showDialog() {
        setVisible(true);
        return finalData;
    }
    public static class Results {
        /**
         * game .jar file
         */
        public final String dir1;
        /**
         * game data directory,
         * null if use default path
         */
        public final String dir2;
        public final boolean separateData;
        public final String Name;
        public final String VersionString;
        public Results(String val1, String val2, boolean val3, String val4, String val5) {
            dir1 = val1;
            dir2 = val2;
            separateData = val3;
            Name = val4;
            VersionString = val5;
        }
    }
}
