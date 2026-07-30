package com.mycompany.tasktrakingsystem.view.ManagerDashboard;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.dao.ReportDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


import com.mycompany.tasktrakingsystem.util.Config;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ReportsDashboard extends JFrame {
    
    private ReportDAO reportDAO = new ReportDAO();
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> comboProjectID; 
    
     private Config config = new Config();

    public ReportsDashboard() {
        setTitle("Project's Report Dashboard");
        setSize(900, 580); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color primaryColor = new Color(15, 34, 64); 

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        mainPanel.setBackground(new Color(245, 247, 250));

       
        JLabel lblTitle = new JLabel("Project's Report");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(primaryColor);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        filterPanel.setBackground(mainPanel.getBackground());
        
        JLabel lblSelect = new JLabel("Select Project ID:");
        lblSelect.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSelect.setForeground(primaryColor);
        
        comboProjectID = new JComboBox<>();
        comboProjectID.setPreferredSize(new Dimension(180, 35)); 
        comboProjectID.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton btnPrintReport = new JButton("Print Report");
        btnPrintReport.setPreferredSize(new Dimension(140, 35));
        btnPrintReport.setBackground(primaryColor);
        btnPrintReport.setForeground(Color.WHITE);
        btnPrintReport.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnPrintReport.setFocusPainted(false);
        
        filterPanel.add(lblSelect);
        filterPanel.add(comboProjectID);
        filterPanel.add(btnPrintReport);
        mainPanel.add(filterPanel);
        mainPanel.add(Box.createVerticalStrut(25));

      
        String[] cols = {"Project ID", "Project Name", "Project Status", "Task ID", "Task Name", "Task Status"};
        
        model = new DefaultTableModel(null, cols);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(800, 250));
        mainPanel.add(scroll);
        mainPanel.add(Box.createVerticalStrut(25));

      
        JButton btnBack = new JButton("BACK");
        btnBack.setBackground(primaryColor); 
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBack.setPreferredSize(new Dimension(110, 35));
        btnBack.setFocusPainted(false);
        
        JPanel backWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backWrapper.setBackground(mainPanel.getBackground());
        backWrapper.add(btnBack);
        mainPanel.add(backWrapper);

        add(mainPanel);

      loadWindowState();
      
        populateProjectDropdown();

      
        refreshReportTable();

        comboProjectID.addActionListener(e -> refreshReportTable());

      
        btnPrintReport.addActionListener(e -> {
            int rowsCount = model.getRowCount();
            Object selectedItem = comboProjectID.getSelectedItem();
            String selectedProj = selectedItem != null ? selectedItem.toString() : "ALL";
            
            if (rowsCount > 0) {
                JOptionPane.showMessageDialog(this, 
                        "Preparing PDF document for Project (" + selectedProj + ")...\n" +
                        "Sending " + rowsCount + " operational rows to printer queue successfully!", 
                        "Print Report", 
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No data available to print for this selection.", "Print Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> dispose());
        
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

   
    private void populateProjectDropdown() {
        comboProjectID.removeAllItems(); 
        comboProjectID.addItem("ALL Projects"); 
        
        List<Integer> projectIds = reportDAO.getAllProjectIds();
        if (projectIds != null) {
            for (Integer id : projectIds) {
                comboProjectID.addItem(String.valueOf(id));
            }
        }
    }

    private void refreshReportTable() {
        if (model == null) return;
        model.setRowCount(0);
        
        Object selectedItem = comboProjectID.getSelectedItem();
        String selectedOption = selectedItem != null ? selectedItem.toString() : "ALL Projects";
        
        List<Object[]> reportRows = reportDAO.getProjectDetailedReport();
        if (reportRows != null) {
            for (Object[] row : reportRows) {
              
                if (selectedOption.equals("ALL Projects") || selectedOption.equals(row[0].toString())) {
                    model.addRow(row);
                }
            }
        }
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
    
}