package com.mycompany.tasktrakingsystem.view.ManagerDashboard;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.dao.DepartmentDAO;
import com.mycompany.tasktrakingsystem.model.Department;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


import com.mycompany.tasktrakingsystem.util.Config;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DepartmentManagement extends JFrame {
    
    private JTextField txtName;
    private JTextField txtID;
    private JTable table;
    private DefaultTableModel model;
    

    private DepartmentDAO deptDAO = new DepartmentDAO();
    
     private Config config = new Config();

    public DepartmentManagement() {
        setTitle("Department Management");
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color primaryColor = new Color(15, 34, 64);
        Color deleteColor = new Color(140, 40, 40);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(240, 244, 247));

        JLabel lblTitle = new JLabel("Department Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        fieldsPanel.setBackground(mainPanel.getBackground());
        fieldsPanel.setMaximumSize(new Dimension(450, 80));

        txtName = new JTextField();
        txtID = new JTextField();

        fieldsPanel.add(new JLabel("Dept Name:"));
        fieldsPanel.add(txtName);
        fieldsPanel.add(new JLabel("Dept ID:"));
        fieldsPanel.add(txtID);

        mainPanel.add(fieldsPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionsPanel.setBackground(mainPanel.getBackground());

        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        JButton btnAdd = new JButton("Add");

        Dimension size = new Dimension(110, 35);
        for(JButton b : new JButton[]{btnDelete, btnUpdate, btnAdd}) {
            b.setPreferredSize(size);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        }
        
        btnDelete.setBackground(primaryColor);
        btnUpdate.setBackground(primaryColor);
        btnAdd.setBackground(primaryColor);

        actionsPanel.add(btnDelete);
        actionsPanel.add(btnUpdate);
        actionsPanel.add(btnAdd);
        mainPanel.add(actionsPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        String[] columnHeaders = {"ID", "Department Name"};
        
        model = new DefaultTableModel(null, columnHeaders);
        table = new JTable(model);
        table.setRowHeight(28);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(500, 180));
        mainPanel.add(scroll);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton btnBack = new JButton("BACK");
        btnBack.setBackground(primaryColor);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBack.setPreferredSize(new Dimension(100, 35));
        
        JPanel backContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backContainer.setBackground(mainPanel.getBackground());
        backContainer.add(btnBack);
        mainPanel.add(backContainer);

        add(mainPanel);

        loadWindowState();

        refreshTableData();

      
        btnAdd.addActionListener(e -> {
            if(!txtID.getText().isEmpty() && !txtName.getText().isEmpty()) {
                try {
                    int id = Integer.parseInt(txtID.getText().trim());
                    String name = txtName.getText().trim();
                    
                   
                    Department dept = new Department(id, name);
                    
                    if(deptDAO.addDepartment(dept)) {
                        JOptionPane.showMessageDialog(this, "Department Added Successfully!");
                        refreshTableData(); 
                        txtID.setText(""); 
                        txtName.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add department. The ID might be duplicated.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID must be a valid number!", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all input fields!");
            }
        });
        
        
        
        
        

       
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                txtID.setText(model.getValueAt(selectedRow, 0).toString());
                txtName.setText(model.getValueAt(selectedRow, 1).toString());
            }
        });

        
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int id = Integer.parseInt(txtID.getText().trim());
                    String name = txtName.getText().trim();
                    
                    Department dept = new Department(id, name);
                    
                    if(deptDAO.updateDepartment(dept)) {
                        JOptionPane.showMessageDialog(this, "Department Updated Successfully!");
                        refreshTableData(); 
                        txtID.setText(""); 
                        txtName.setText("");
                        table.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(this, "Update failed.");
                    }
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid entry values.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a department row from the table to update!");
            }
        });

        
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this department?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    if(deptDAO.deleteDepartment(id)) {
                        JOptionPane.showMessageDialog(this, "Department Deleted!");
                        refreshTableData(); 
                        txtID.setText(""); 
                        txtName.setText("");
                        table.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(this, "Delete failed! This department may be linked to other records.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a department row from the table to delete!");
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

   
    private void refreshTableData() {
        model.setRowCount(0); 
        List<Department> departments = deptDAO.getAllDepartments();
        for (Department d : departments) {
            model.addRow(new Object[]{d.getDepId(), d.getDepName()});
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