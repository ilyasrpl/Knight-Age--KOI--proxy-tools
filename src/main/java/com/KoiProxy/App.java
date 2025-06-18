package com.KoiProxy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    static public JTextArea textAreaLeft;
    static public JTextArea textAreaRight;
    static public JCheckBox rawModeCheckBox;
    static public JCheckBox blockModeCheckBox;
    static public Packet[] leftPackets;
    static public Packet[] rightPackets;
    static public String leftLog = "";
    static public String rightLog = "";
    public static final List<PlayerSession> activeSessions = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        // Buat frame
        JFrame frame = new JFrame("KOI snip proxy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Panel untuk input number dan button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField hostField = new JTextField("hsindo20.teamobi.com");
        hostField.setPreferredSize(new Dimension(150, 25));
        JSpinner PortField = new JSpinner(new SpinnerNumberModel(19130, 0, Integer.MAX_VALUE, 1));
        PortField.setPreferredSize(new Dimension(100, 25));
        JButton clearButton = new JButton("CLEAR");
        JButton button = new JButton("RUN");
        JButton sessionButton = new JButton("Session"); // New session button
        topPanel.add(new JLabel("Host: "));
        topPanel.add(hostField);
        topPanel.add(new JLabel("Port: "));
        topPanel.add(PortField);
        topPanel.add(button);
        topPanel.add(clearButton);
        topPanel.add(sessionButton); // Add session button to topPanel

        rawModeCheckBox = new JCheckBox("Raw Mode", true);
        blockModeCheckBox = new JCheckBox("Block Mode", true);
        topPanel.add(rawModeCheckBox);
        topPanel.add(blockModeCheckBox);

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
            rightLog = "";
            textAreaLeft.setText("");
            textAreaRight.setText("");
        });

        // Add action listener for the Session button
        sessionButton.addActionListener(e -> {
            new SessionFrame(App.activeSessions); // Create a new instance of SessionFrame with the class property
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
