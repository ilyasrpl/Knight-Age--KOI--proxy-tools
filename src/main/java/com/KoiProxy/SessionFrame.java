package com.KoiProxy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SessionFrame extends JFrame {

    private JTable sessionTable;
    private DefaultTableModel tableModel;

    public SessionFrame() {
        setTitle("Session Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600); // Increased size for better table visibility
        setLocationRelativeTo(null);

        // Table setup
        String[] columnNames = {"ID", "Email", "password", "Name", "Level", "exp", "gold", "Socket Info"};
        tableModel = new DefaultTableModel(columnNames, 0);
        sessionTable = new JTable(tableModel);
        sessionTable.setFillsViewportHeight(true); // Table uses the entire height of the viewport

        JScrollPane scrollPane = new JScrollPane(sessionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Populate table with data
        updateSessionTable(App.activeSessions); // Use the static activeSessions from App

        // Setup auto-refresh timer
        Timer timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> updateSessionTable(App.activeSessions));
            }
        }, 0, 1000); // Start immediately, refresh every 1 second (1000 ms)

        setVisible(true);
    }

    public void updateSessionTable(List<PlayerSession> playerSessions) {
        tableModel.setRowCount(0); // Clear existing data
        if (playerSessions != null) {
            for (PlayerSession session : playerSessions) {
                Object[] rowData = {
                    session.id,
                    session.email != null ? session.email : "N/A",
                    session.password != null ? session.password : "N/A",
                    session.name != null ? session.name : "N/A",
                    session.level,
                    session.expPercent,
                    session.gold,
                    session.socket != null ? session.socket.getRemoteSocketAddress().toString() : "N/A"
                };
                tableModel.addRow(rowData);
            }
        }
    }
}
