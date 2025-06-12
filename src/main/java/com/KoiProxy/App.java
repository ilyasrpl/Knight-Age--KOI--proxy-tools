package com.KoiProxy;

import javax.swing.*;
import java.awt.*;

public class App {

    static public JTextArea textArea1;
    static public JTextArea textArea2;
    public static void main(String[] args) {
        // Buat frame
        JFrame frame = new JFrame("Simple GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400); // Tinggi ditambah untuk komponen baru
        frame.setLayout(new BorderLayout(10, 10));

        // Panel untuk input number dan button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField hostField = new JTextField("hsindo20.teamobi.com");
        hostField.setPreferredSize(new Dimension(150, 25));
        JSpinner PortField = new JSpinner(new SpinnerNumberModel(19130, 0, Integer.MAX_VALUE, 1));
        PortField.setPreferredSize(new Dimension(100, 25));
        JButton button = new JButton("RUN");
        topPanel.add(new JLabel("Host: "));
        topPanel.add(hostField);
        topPanel.add(new JLabel("Port: "));
        topPanel.add(PortField);
        topPanel.add(button);

        // Panel untuk text area
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        textArea1 = new JTextArea();
        textArea2 = new JTextArea();

        // Tambahkan scroll pane untuk text area
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
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
            textArea1.setText(host + ":" + port + "\n");
            new Thread(() -> TcpServer.start()).start();
            button.setEnabled(false);
        });
    }

    static public void addText1(String text) {
        textArea1.append(text + "\n");
    }

    static public void addText2(String text) {
        textArea2.append(text + "\n");
    }
}
