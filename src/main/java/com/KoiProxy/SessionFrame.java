package com.KoiProxy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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
        String[] columnNames = { "ID", "Name", "Hp", "Level", "exp", "gold", "location", "Zone" };
        tableModel = new DefaultTableModel(columnNames, 0);
        sessionTable = new JTable(tableModel);
        sessionTable.setFillsViewportHeight(true); // Table uses the entire height of the viewport

        // Set preferred column widths
        TableColumnModel columnModel = sessionTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25); // ID
        columnModel.getColumn(1).setPreferredWidth(100); // Name
        columnModel.getColumn(2).setPreferredWidth(100); // Hp
        columnModel.getColumn(3).setPreferredWidth(25); // Level
        columnModel.getColumn(4).setPreferredWidth(25); // exp
        columnModel.getColumn(7).setPreferredWidth(25); // zone

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
                        session.name != null ? session.name : "N/A",
                        session.hpNow + "/" + session.hpMax,
                        session.level,
                        session.expPercent,
                        session.gold,
                        session.locationNow,
                        session.zone
                };
                tableModel.addRow(rowData);
            }
        }
    }
}
