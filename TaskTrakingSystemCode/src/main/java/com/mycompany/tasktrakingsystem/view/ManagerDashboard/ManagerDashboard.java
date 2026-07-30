/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.view.ManagerDashboard;

/**
 *
 * @author ENJAZ
 */


import com.mycompany.tasktrakingsystem.util.Config;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;



public class ManagerDashboard extends JFrame {
    
    private Config config = new Config();

    public ManagerDashboard() {
        setTitle("Manager Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color primaryColor = new Color(15, 34, 64); 
        Color backgroundColor = new Color(240, 244, 248);
        Color logoutColor = new Color(140, 40, 40); 

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Manager Dashboard", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(primaryColor);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel sidebarWrapper = new JPanel(new BorderLayout());
        sidebarWrapper.setBackground(backgroundColor);
        sidebarWrapper.setPreferredSize(new Dimension(240, 0));

        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        menuPanel.setBackground(backgroundColor);

        JButton btnDept = new JButton("Department Management");
        JButton btnEmp = new JButton("Employee Management");
        JButton btnReports = new JButton("Reports");

        JButton[] menuButtons = {btnDept, btnEmp, btnReports};

        for (JButton btn : menuButtons) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setBackground(primaryColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            menuPanel.add(btn);
        }
        sidebarWrapper.add(menuPanel, BorderLayout.NORTH);

        JButton btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(logoutColor);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        sidebarWrapper.add(btnLogout, BorderLayout.SOUTH);
        mainPanel.add(sidebarWrapper, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        
        JLabel lblMessage = new JLabel("Select a category to view details...", JLabel.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblMessage.setForeground(Color.GRAY);
        contentPanel.add(lblMessage);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        
loadWindowState();
       
        btnDept.addActionListener(e -> new DepartmentManagement().setVisible(true));
        btnEmp.addActionListener(e -> new EmployeeManagement().setVisible(true));
        btnReports.addActionListener(e -> new ReportsDashboard().setVisible(true));
       

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        });
   
    addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {

        config.setProperty("width", String.valueOf(getWidth()));
        config.setProperty("height", String.valueOf(getHeight()));

        config.setProperty("x", String.valueOf(getX()));
        config.setProperty("y", String.valueOf(getY()));

        config.setProperty("state",
                String.valueOf(getExtendedState()));

        config.save();
    }
});
    
    }
    
    
     private void loadWindowState() {
    try {
        setSize(
            Integer.parseInt(config.getProperty("width", "800")),
            Integer.parseInt(config.getProperty("height", "750"))
        );

        setLocation(
            Integer.parseInt(config.getProperty("x", "100")),
            Integer.parseInt(config.getProperty("y", "100"))
        );

        setExtendedState(
            Integer.parseInt(config.getProperty("state", "0"))
        );
    } catch (Exception e) {
        setLocationRelativeTo(null);
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagerDashboard().setVisible(true));
    }
}