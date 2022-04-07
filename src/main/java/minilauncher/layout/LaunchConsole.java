package minilauncher.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import minilauncher.handler.TextAreaOutputStream;

public class LaunchConsole extends Layout {
    public PrintStream printStream;
    public JTextField inputField = null;
    public LaunchConsole() {this(true);}
    public LaunchConsole(boolean inputEnabled) {
        super(new Layout.Properties());
        setTitle("Launcher Console");
        setSize(900, 500);
        setLocationRelativeTo(null);
        JTextArea ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.WHITE);
        ta.setSelectedTextColor(Color.BLACK);
        ta.setSelectionColor(Color.WHITE);
        ta.setEditable(false);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        printStream = new PrintStream( taos );
        
        if (inputEnabled) {
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new JScrollPane(ta));
            inputField = new JTextField();
            inputField.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
            inputField.setBackground(Color.BLACK);
            inputField.setForeground(Color.WHITE);
            inputField.setSelectedTextColor(Color.BLACK);
            inputField.setSelectionColor(Color.WHITE);
            inputField.setCaretColor(Color.WHITE);
            inputField.getCaret().setBlinkRate(0);
            mainPanel.add(inputField, BorderLayout.SOUTH);
            add(mainPanel);
        } else {
            add(new JScrollPane(ta));
        }
    }
}
