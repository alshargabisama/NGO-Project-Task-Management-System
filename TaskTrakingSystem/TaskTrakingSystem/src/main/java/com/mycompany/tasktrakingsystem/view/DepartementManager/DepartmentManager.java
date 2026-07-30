package com.mycompany.tasktrakingsystem.view.DepartementManager;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.dao.DepartmentManagerDAO;
import com.mycompany.tasktrakingsystem.view.Login.LoginView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;



import com.mycompany.tasktrakingsystem.util.Config;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class DepartmentManager extends JFrame {

    private JTextField txtProjId, txtProjName, txtProjDesc, txtProjStart, txtProjEnd;
    private JComboBox<String> cmbProjStatus;
    
    private JTextField txtTaskProjId, txtTaskName, txtTaskDesc, txtTaskStart, txtTaskEnd;
    private JComboBox<String> cmbEmployees; 
    private JComboBox<String> cmbTaskStatus;
    
    private DefaultTableModel tableModel;
    private JTable dataTable;
    
    
    private JTable dataTablee;
    
    private Config config = new Config();
    
    

    public DepartmentManager() {
        setTitle("Department Manager - Projects & Tasks Control");
        setSize(1150, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color primaryColor = new Color(15, 34, 64);     
        Color backgroundColor = new Color(244, 246, 249);
        Color deleteColor = new Color(140, 40, 40);      

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitle = new JLabel("Department Manager - Projects & Tasks Control", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

   
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        JLabel lblProjSection = new JLabel(" Project Details ");
        lblProjSection.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblProjSection.setForeground(primaryColor);
        formContainer.add(lblProjSection, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; formContainer.add(createStyledLabel("Project ID:", labelFont, primaryColor), gbc);
        gbc.gridx = 1; txtProjId = new JTextField(15); formContainer.add(txtProjId, gbc);
        
        gbc.gridx = 2; formContainer.add(createStyledLabel("Project Name:", labelFont, primaryColor), gbc);
        gbc.gridx = 3; txtProjName = new JTextField(15); formContainer.add(txtProjName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; formContainer.add(createStyledLabel("Project Description:", labelFont, primaryColor), gbc);
        gbc.gridx = 1; txtProjDesc = new JTextField(15); formContainer.add(txtProjDesc, gbc);

        gbc.gridx = 2; gbc.gridy = 2; formContainer.add(createStyledLabel("Project Status:", labelFont, primaryColor), gbc);
        gbc.gridx = 3; cmbProjStatus = new JComboBox<>(new String[]{"Planned", "Active", "On Hold", "Finished"}); formContainer.add(cmbProjStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formContainer.add(createStyledLabel("Start Date:", labelFont, primaryColor), gbc);
        gbc.gridx = 1; txtProjStart = new JTextField(15); formContainer.add(txtProjStart, gbc);
        
        gbc.gridx = 2; formContainer.add(createStyledLabel("End Date:", labelFont, primaryColor), gbc);
        gbc.gridx = 3; txtProjEnd = new JTextField(15); formContainer.add(txtProjEnd, gbc);

      
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        formContainer.add(Box.createVerticalStrut(10), gbc);

        gbc.gridy = 5;
        JLabel lblTaskSection = new JLabel(" Task & Assignment Details ");
        lblTaskSection.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTaskSection.setForeground(primaryColor);
        formContainer.add(lblTaskSection, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 6; formContainer.add(createStyledLabel("Project ID (for Task):", labelFont, primaryColor), gbc);
        gbc.gridx = 1; txtTaskProjId = new JTextField(15); formContainer.add(txtTaskProjId, gbc);

        gbc.gridx = 2; formContainer.add(createStyledLabel("Task Name:", labelFont, primaryColor), gbc);
        gbc.gridx = 3; txtTaskName = new JTextField(15); formContainer.add(txtTaskName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; formContainer.add(createStyledLabel("Task Description:", labelFont, primaryColor), gbc);
        gbc.gridx = 1; txtTaskDesc = new JTextField(15); formContainer.add(txtTaskDesc, gbc);

        gbc.gridx = 2; formContainer.add(createStyledLabel("Task Status:", labelFont, primaryColor), gbc);
        gbc.gridx = 3; cmbTaskStatus = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"}); formContainer.add(cmbTaskStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 8; formContainer.add(createStyledLabel("Task Start Date:", labelFont, primaryColor), gbc);
        gbc.gridx = 1; txtTaskStart = new JTextField(15); formContainer.add(txtTaskStart, gbc);
        
        gbc.gridx = 2; formContainer.add(createStyledLabel("Task End Date:", labelFont, primaryColor), gbc);
        gbc.gridx = 3; txtTaskEnd = new JTextField(15); formContainer.add(txtTaskEnd, gbc);

        gbc.gridx = 0; gbc.gridy = 9; formContainer.add(createStyledLabel("Assign To Employee:", labelFont, primaryColor), gbc);
        cmbEmployees = new JComboBox<>(new String[]{" Select Employee "}); 
        gbc.gridx = 1; formContainer.add(cmbEmployees, gbc);

        formContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.setMaximumSize(new Dimension(1050, 340));
        mainPanel.add(formContainer);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        actionPanel.setBackground(backgroundColor);
        actionPanel.setMaximumSize(new Dimension(1050, 45));

        JButton btnAddProj = new JButton("Add Project");
        JButton btnDelProj = new JButton("Delete Project");
        JButton btnAddTask = new JButton("Add Task");
        JButton btnDelTask = new JButton("Delete Task");

        Dimension btnSize = new Dimension(140, 38);
        for (JButton btn : new JButton[]{btnAddProj, btnDelProj, btnAddTask, btnDelTask}) {
            btn.setPreferredSize(btnSize);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setForeground(Color.WHITE);
        }
        btnAddProj.setBackground(primaryColor);
        btnAddTask.setBackground(primaryColor);
        btnDelProj.setBackground(primaryColor);
        btnDelTask.setBackground(primaryColor);

        actionPanel.add(btnAddProj);
        actionPanel.add(btnDelProj);
        actionPanel.add(Box.createHorizontalStrut(30)); 
        actionPanel.add(btnAddTask);
        actionPanel.add(btnDelTask);
        
        mainPanel.add(actionPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        
        String[] columns = {
            "Proj Name", "Proj Desc", "Proj Start", "Proj End", "Proj Status",
            "Task Name", "Task Desc", "Task Start", "Task End", "Assigned Employee", "Task Status"
        };
        
        tableModel = new DefaultTableModel(null, columns);
        dataTablee = new JTable(tableModel);
        dataTablee.setRowHeight(30);
        dataTablee.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dataTablee.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
        
        JScrollPane scrollPane = new JScrollPane(dataTablee);
        scrollPane.setPreferredSize(new Dimension(1050, 240));
        scrollPane.setMaximumSize(new Dimension(1050, 240)); 
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT); 
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(15));

      
        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.setBackground(deleteColor); 
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setPreferredSize(new Dimension(120, 35));
        btnLogout.setMaximumSize(new Dimension(120, 35));
        btnLogout.setFocusPainted(false);
        
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
        footerPanel.setBackground(backgroundColor);
        footerPanel.setMaximumSize(new Dimension(1050, 40));
        
        footerPanel.add(Box.createHorizontalGlue()); 
        footerPanel.add(btnLogout);
        mainPanel.add(footerPanel);

        add(mainPanel);
     
        loadWindowState();

addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {

        config.setProperty("width", String.valueOf(getWidth()));
        config.setProperty("height", String.valueOf(getHeight()));
        config.setProperty("x", String.valueOf(getX()));
        config.setProperty("y", String.valueOf(getY()));
        config.setProperty("state", String.valueOf(getExtendedState()));

        config.save();
    }
});



       
        btnAddProj.addActionListener(e -> {
            if(txtProjId.getText().isEmpty() || txtProjName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Project ID and Name are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int pid = Integer.parseInt(txtProjId.getText().trim());
                DepartmentManagerDAO dao = new DepartmentManagerDAO();
                dao.addProject(pid, txtProjName.getText().trim(), txtProjDesc.getText().trim(), txtProjStart.getText().trim(), txtProjEnd.getText().trim(), cmbProjStatus.getSelectedItem().toString());
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Project Added Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Project ID must be a number!");
            }
        });

        
        btnDelProj.addActionListener(e -> {
            if(txtProjName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter or select a Project Name to delete!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this project?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DepartmentManagerDAO dao = new DepartmentManagerDAO();
                dao.deleteProject(txtProjName.getText().trim());
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Project Deleted!");
            }
        });

       
        btnAddTask.addActionListener(e -> {
            if(txtTaskProjId.getText().isEmpty() || txtTaskName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Project ID (for Task) and Task Name are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int pid = Integer.parseInt(txtTaskProjId.getText().trim());
                DepartmentManagerDAO dao = new DepartmentManagerDAO();
                dao.addTask(pid, txtTaskName.getText().trim(), txtTaskDesc.getText().trim(), txtTaskStart.getText().trim(), txtTaskEnd.getText().trim(), cmbTaskStatus.getSelectedItem().toString());
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Task Added Successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Project ID must be a number!");
            }
        });

       
        btnDelTask.addActionListener(e -> {
            if(txtTaskName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select or enter a Task Name to delete!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DepartmentManagerDAO dao = new DepartmentManagerDAO();
                dao.deleteTask(txtTaskName.getText().trim());
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Task Deleted!");
            }
        });

     
        dataTablee.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = dataTablee.getSelectedRow();
            if (selectedRow != -1) {
                txtProjName.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtProjDesc.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtProjStart.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtProjEnd.setText(tableModel.getValueAt(selectedRow, 3).toString());
                cmbProjStatus.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
                
                txtTaskName.setText(tableModel.getValueAt(selectedRow, 5).toString());
                txtTaskDesc.setText(tableModel.getValueAt(selectedRow, 6).toString());
                txtTaskStart.setText(tableModel.getValueAt(selectedRow, 7).toString());
                txtTaskEnd.setText(tableModel.getValueAt(selectedRow, 8).toString());
                cmbEmployees.setSelectedItem(tableModel.getValueAt(selectedRow, 9).toString());
                cmbTaskStatus.setSelectedItem(tableModel.getValueAt(selectedRow, 10).toString());
            }
        });
        
        
        

        btnLogout.addActionListener(e -> {
            int exitConfirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout System", JOptionPane.YES_NO_OPTION);
            if (exitConfirm == JOptionPane.YES_OPTION) {
                this.dispose(); 
                
              
                new LoginView().setVisible(true); 
            }
        });
        
        loadEmployeesFromDatabase();
        loadTableData(); 
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private void clearFields() {
        txtProjId.setText(""); txtProjName.setText(""); txtProjDesc.setText(""); txtProjStart.setText(""); txtProjEnd.setText("");
        txtTaskProjId.setText(""); txtTaskName.setText(""); txtTaskDesc.setText(""); txtTaskStart.setText(""); txtTaskEnd.setText("");
        cmbProjStatus.setSelectedIndex(0); cmbTaskStatus.setSelectedIndex(0); cmbEmployees.setSelectedIndex(0);
    }

    private void loadEmployeesFromDatabase() {
        cmbEmployees.removeAllItems(); 
        cmbEmployees.addItem(" Select Employee ");
        DepartmentManagerDAO dao = new DepartmentManagerDAO();
        List<String> names = dao.getAllAssignees();
        if (names != null && !names.isEmpty()) {
            for (String name : names) {
                cmbEmployees.addItem(name);
            }
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0); 
        DepartmentManagerDAO dao = new DepartmentManagerDAO();
        List<Object[]> data = dao.getAllProjectData();
        for (Object[] row : data) {
            tableModel.addRow(row); 
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
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DepartmentManager().setVisible(true));
    }
}