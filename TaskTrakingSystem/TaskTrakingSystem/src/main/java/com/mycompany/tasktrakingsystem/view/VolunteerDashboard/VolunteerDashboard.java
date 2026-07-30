/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.view.VolunteerDashboard;

/**
 *
 * @author ENJAZ
 */


import com.mycompany.tasktrakingsystem.dao.ProjectDAO;
import com.mycompany.tasktrakingsystem.view.Login.UserSession;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.mycompany.tasktrakingsystem.dao.TaskDAO;
import com.mycompany.tasktrakingsystem.model.Project;
import com.mycompany.tasktrakingsystem.model.Task;
import com.mycompany.tasktrakingsystem.util.Config;
import java.util.List;
import javax.swing.event.TableModelEvent;
public class VolunteerDashboard extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);
    private Color primaryColor = new Color(15, 34, 64);
    private SidebarButton selectedButton;
    
    private JTable taskTable;
    private JTable projectTable;
    
         private Config config = new Config();

    public VolunteerDashboard() {
        setTitle("Volunteer Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 246, 247));
        setLayout(new BorderLayout(10, 0));

        
        JPanel sidebar = new JPanel(new GridLayout(12, 1, 0, 5));
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));

        JLabel lblHeader = new JLabel("Volunteer Dashboard", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblHeader.setForeground(primaryColor);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        sidebar.add(lblHeader);

        SidebarButton btnTasks = new SidebarButton("My Tasks");
        SidebarButton btnProjects = new SidebarButton("My Projects");
        sidebar.add(btnTasks);
        sidebar.add(btnProjects);
        add(sidebar, BorderLayout.WEST);

      
        taskTable = createTable(new String[]{"Task ID", "Title", "Status", "End Date"});
        projectTable = createTable(new String[]{"Project ID", "Name", "Deadline"});
        
        contentPanel.add(wrapInPanel("My Tasks", taskTable), "Tasks");
        contentPanel.add(wrapInPanel("My Projects", projectTable), "Projects");
        
        add(contentPanel, BorderLayout.CENTER);
        
         loadWindowState();

        btnTasks.addActionListener(e -> { selectButton(btnTasks); cardLayout.show(contentPanel, "Tasks"); });
        btnProjects.addActionListener(e -> { selectButton(btnProjects); cardLayout.show(contentPanel, "Projects"); });
        
        selectButton(btnTasks);
        
        SidebarButton btnLogout = new SidebarButton("Logout");
        btnLogout.setForeground(new Color(200, 50, 50));
        sidebar.add(btnLogout);

        btnLogout.addActionListener(e -> {
            dispose();
            new com.mycompany.tasktrakingsystem.view.Login.LoginView().setVisible(true);
        });
        
        loadData();
        
       
     
        taskTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int taskId = (int) taskTable.getValueAt(row, 0);
                String newStatus = (String) taskTable.getValueAt(row, 2);
                String newEndDate = (String) taskTable.getValueAt(row, 3);
                
              
                new TaskDAO().updateTaskStatusAndDate(taskId, newStatus, java.sql.Date.valueOf(newEndDate));
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

    private JTable createTable(String[] columns) {
        JTable table = new JTable(new DefaultTableModel(new Object[][]{}, columns));
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        
       
        if (columns.length > 3) {
       
JComboBox<String> statusCombo = new JComboBox<>(new String[]{"pending", "in_progress", "completed", "cancelled"});
            table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(statusCombo));
        }

        JTableHeader header = table.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return table;
    }

    private JPanel wrapInPanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }


    private void loadData() {
        int userId = UserSession.getLoggedInUserId();
     
       
    System.out.println("Current User ID in Session: " + userId);
        TaskDAO taskDAO = new TaskDAO();
        
       
        List<Task> tasks = taskDAO.getTasksByUserId(userId);
        
        DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
        model.setRowCount(0); 
        
        
        for (Task t : tasks) {
            model.addRow(new Object[]{
                t.getTaskId(), 
                t.getTaskTitle(), 
                t.getStatus(), 
                t.getEndDate()
            });
        }
       
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> myProjects = projectDAO.getProjectsByUserId(userId);
        
        DefaultTableModel projectModel = (DefaultTableModel) projectTable.getModel();
        projectModel.setRowCount(0); 
        
        for (Project p : myProjects) {
            projectModel.addRow(new Object[]{
                p.getProjId(), 
                p.getProjName(), 
                p.getEndDate() 
            });
        }
        
    }

    private void selectButton(SidebarButton btn) {
        if (selectedButton != null) selectedButton.setSelected(false);
        selectedButton = btn;
        selectedButton.setSelected(true);
    }

    class SidebarButton extends JButton {
        private boolean isSelected = false;
        public SidebarButton(String text) {
            super(text);
            setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { setForeground(primaryColor.brighter()); }
                public void mouseExited(MouseEvent e) { if (!isSelected) setForeground(Color.GRAY); }
            });
        }
        public void setSelected(boolean b) {
            isSelected = b;
            setForeground(b ? primaryColor : Color.GRAY);
            setOpaque(b);
            if (b) setBackground(new Color(230, 240, 255));
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

