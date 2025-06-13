package com.KoiProxy;

import javax.swing.*;
import java.awt.*;

public class App {

    static public JTextArea textAreaLeft;
    static public JTextArea textAreaRight;
    static public String leftLog = "";
    static public String rightLog = "";
    static public JCheckBox rawBox = new JCheckBox("raw log");
    public static void main(String[] args) {
        // Buat frame
        JFrame frame = new JFrame("KOI snip proxy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400); // Tinggi ditambah untuk komponen baru
        frame.setLayout(new BorderLayout(10, 10));

        // Panel untuk input number dan button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField hostField = new JTextField("hsindo20.teamobi.com");
        hostField.setPreferredSize(new Dimension(150, 25));
        JSpinner PortField = new JSpinner(new SpinnerNumberModel(19130, 0, Integer.MAX_VALUE, 1));
        PortField.setPreferredSize(new Dimension(100, 25));
        JButton clearButton = new JButton("CLEAR");
        JButton button = new JButton("RUN");
        rawBox.setSelected(true);
        topPanel.add(new JLabel("Host: "));
        topPanel.add(hostField);
        topPanel.add(new JLabel("Port: "));
        topPanel.add(PortField);
        topPanel.add(button);
        topPanel.add(clearButton);
        topPanel.add(rawBox);

        // Panel untuk text area
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        textAreaLeft = new JTextArea();
        textAreaRight = new JTextArea();

        // Tambahkan scroll pane untuk text area
        JScrollPane scrollPane1 = new JScrollPane(textAreaLeft);
        JScrollPane scrollPane2 = new JScrollPane(textAreaRight);
        centerPanel.add(scrollPane1);
        centerPanel.add(scrollPane2);

        // Tambahkan panel ke frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Tampilkan frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Tambahkan action listener untuk button RUN
        button.addActionListener(e -> {
            String host = hostField.getText();
            int port = (Integer) PortField.getValue();
            AppEndpoint.SetHost(host);
            AppEndpoint.SetPort(port);
            new Thread(() -> TcpServer.start()).start();
            button.setEnabled(false);
        });

        clearButton.addActionListener(e -> {
            leftLog = "";
            rightLog ="";
            textAreaLeft.setText("");
            textAreaRight.setText("");
        });
    }

    static public void addLeftLog(String text) {
        leftLog = leftLog + text + "\n";
    }

    static public void addRightLog(String text) {
        rightLog = rightLog + text + "\n";
    }

    static public void refreshTAreaLeft() {
        textAreaLeft.setText(leftLog);
        textAreaLeft.setCaretPosition(textAreaLeft.getDocument().getLength());
    }

    static public void refreshTAreaRight() {
        textAreaRight.setText(rightLog);
        textAreaRight.setCaretPosition(textAreaRight.getDocument().getLength());
    }
}
