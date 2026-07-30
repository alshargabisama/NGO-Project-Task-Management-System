package com.mycompany.tasktrakingsystem.view.ManagerDashboard;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.dao.EmployeeDAO;
import com.mycompany.tasktrakingsystem.model.Employee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;


import com.mycompany.tasktrakingsystem.util.Config;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeManagement extends JFrame {

  
    private JTextField[] fields = new JTextField[4];
    private JTable table;
    private DefaultTableModel model;
    
   
    private EmployeeDAO empDAO = new EmployeeDAO();
    
     private Config config = new Config();

    public EmployeeManagement() {
        setTitle("Employee Management");
        setSize(800, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color primaryColor = new Color(15, 34, 64);
        Color backgroundColor = new Color(244, 246, 249);
        Color deleteColor = new Color(140, 40, 40);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel lblTitle = new JLabel("Employee Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(backgroundColor);
        formPanel.setMaximumSize(new Dimension(500, 180));

        
        String[] labels = {"Enter ID:", "Employee Name:", "Job Title:", "Role (Manager/Employee):"};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            fields[i] = new JTextField();
            fields[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            formPanel.add(lbl);
            formPanel.add(fields[i]);
        }
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(backgroundColor);

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        Dimension btnSize = new Dimension(110, 35);
        for (JButton btn : new JButton[]{btnAdd, btnUpdate, btnDelete}) {
            btn.setPreferredSize(btnSize);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setForeground(Color.WHITE);
        }
        
        btnAdd.setBackground(primaryColor);
        btnUpdate.setBackground(primaryColor);
        btnDelete.setBackground(primaryColor);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));

      
        String[] columns = {"ID", "Employee Name", "Job Title", "Role"};

       
        model = new DefaultTableModel(null, columns);
        table = new JTable(model);
        table.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 250));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(20));

        JButton btnBack = new JButton("BACK");
        btnBack.setBackground(primaryColor);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setPreferredSize(new Dimension(120, 35));
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnBack);

        add(mainPanel);
        
        
        loadWindowState();

        refreshTableData();

      
        btnAdd.addActionListener(e -> {
            if (fields[0].getText().isEmpty() || fields[1].getText().isEmpty() || fields[2].getText().isEmpty() || fields[3].getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all input fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(fields[0].getText().trim());
                String name = fields[1].getText().trim();
                String title = fields[2].getText().trim();
                String role = fields[3].getText().trim();

              
                Employee emp = new Employee(id, name, name.toLowerCase() + "@org.com", "Taiz", "000000", "Male", 
                                            LocalDate.of(2000, 1, 1), "Active", "pass123", LocalDate.now(), title, role) {
                    @Override
                    public double calculateSalary() { return 0.0; }
                };

                if (empDAO.addEmployee(emp)) {
                    JOptionPane.showMessageDialog(this, "Employee Added Successfully to database!");
                    refreshTableData(); 
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add employee. Duplicated User ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID must be a numerical value!", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                fields[0].setText(model.getValueAt(selectedRow, 0).toString());
                fields[1].setText(model.getValueAt(selectedRow, 1).toString());
                fields[2].setText(model.getValueAt(selectedRow, 2).toString());
                fields[3].setText(model.getValueAt(selectedRow, 3).toString());
                fields[0].setEditable(false); 
            }
        });

     
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(fields[0].getText().trim());
                String name = fields[1].getText().trim();
                String title = fields[2].getText().trim();
                String role = fields[3].getText().trim();

                Employee emp = new Employee(id, name, name.toLowerCase() + "@org.com", "Taiz", "000000", "Male", 
                                            LocalDate.of(2000, 1, 1), "Active", "pass123", LocalDate.now(), title, role) {
                    @Override
                    public double calculateSalary() { return 0.0; }
                };

                if (empDAO.updateEmployee(emp)) {
                    JOptionPane.showMessageDialog(this, "Employee Updated Successfully!");
                    refreshTableData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee row from the table to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

      
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee from system database?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (empDAO.deleteEmployee(id)) {
                        JOptionPane.showMessageDialog(this, "Employee Deleted Successfully!");
                        refreshTableData();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Delete failed! This employee might be linked to operational project tasks.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an employee row from the table to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
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
        List<Employee> employees = empDAO.getAllEmployees();
        for (Employee emp : employees) {
         
            model.addRow(new Object[]{emp.getUserId(), emp.getUserName(), emp.getJobTitle(), emp.getRole()});
        }
    }

    private void clearFields() {
        for (JTextField field : fields) {
            field.setText("");
        }
        fields[0].setEditable(true);
        table.clearSelection();
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


