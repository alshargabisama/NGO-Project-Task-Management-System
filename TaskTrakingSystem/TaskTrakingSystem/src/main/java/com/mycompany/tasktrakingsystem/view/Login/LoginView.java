package com.mycompany.tasktrakingsystem.view.Login;

import com.mycompany.tasktrakingsystem.service.AuthService;
import com.mycompany.tasktrakingsystem.util.Config;

import com.mycompany.tasktrakingsystem.view.EmployeeDashboard.EmployeeDashboard;
import com.mycompany.tasktrakingsystem.view.ManagerDashboard.ManagerDashboard;
import com.mycompany.tasktrakingsystem.view.DepartementManager.DepartmentManager;
import com.mycompany.tasktrakingsystem.view.VolunteerDashboard.VolunteerDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {

    private JPanel mainPanel;
    private JLabel lblTitle, lblUsername, lblPassword;
    private PlaceholderTextField txtUsername;
    private PlaceholderPasswordField txtPassword;
    private RoundedButton btnLogin, btnCancel;
    
    private JCheckBox chkRemember;
    
         private Config config = new Config();

    public LoginView() {
        setTitle("Task Tracking System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setResizable(false);

        Color primaryColor = new Color(15, 34, 64);
        Color backgroundColor = new Color(245, 247, 250);
        Color textColor = new Color(108, 117, 125);
        Font customFont = new Font("Segoe UI", Font.BOLD, 14);

        mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(null);
        add(mainPanel);

        lblTitle = new JLabel("Login to the System", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(primaryColor);
        lblTitle.setBounds(50, 30, 350, 40);
        mainPanel.add(lblTitle);

        lblUsername = new JLabel("Username:");
        lblUsername.setFont(customFont);
        lblUsername.setForeground(textColor);
        lblUsername.setBounds(50, 100, 100, 30);
        mainPanel.add(lblUsername);

        txtUsername = new PlaceholderTextField("Enter your username...");
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBounds(160, 100, 220, 35);
        mainPanel.add(txtUsername);

        lblPassword = new JLabel("Password:");
        lblPassword.setFont(customFont);
        lblPassword.setForeground(textColor);
        lblPassword.setBounds(50, 150, 100, 30);
        mainPanel.add(lblPassword);

        txtPassword = new PlaceholderPasswordField("Enter your password...");
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(160, 150, 220, 35);
        mainPanel.add(txtPassword);

        btnLogin = new RoundedButton("Login", primaryColor);
        btnLogin.setFont(customFont);
        btnLogin.setBounds(230, 220, 150, 35);
        mainPanel.add(btnLogin);

        btnCancel = new RoundedButton("Cancel", primaryColor);
        btnCancel.setFont(customFont);
        btnCancel.setBounds(50, 220, 150, 35);
        mainPanel.add(btnCancel);
        
        chkRemember = new JCheckBox("Remember Me");
chkRemember.setBounds(160, 190, 200, 25);
mainPanel.add(chkRemember);
        
        
        loadWindowState();
        
        
        
        
         JPopupMenu menu = new JPopupMenu();

JMenuItem properties = new JMenuItem("Properties");

properties.addActionListener(e -> {
    JOptionPane.showMessageDialog(this,
        "Width : " + getWidth()
        + "\nHeight : " + getHeight()
        + "\nX : " + getX()
        + "\nY : " + getY()
        + "\nTitle : " + getTitle(),
        "Window Properties",
        JOptionPane.INFORMATION_MESSAGE);
});

menu.add(properties);



addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            menu.show(LoginView.this, e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            menu.show(LoginView.this, e.getX(), e.getY());
        }
    }
});
        
        
        
        
        
        txtUsername.setText(config.getProperty("remember_username", ""));
txtPassword.setText(config.getProperty("remember_password", ""));
chkRemember.setSelected(Boolean.parseBoolean(
        config.getProperty("remember_checked", "false")
));





        btnCancel.addActionListener(e -> System.exit(0));

        btnLogin.addActionListener(e -> {
            
            
            String username = txtUsername.getText().equals("Enter your username...") ? "" : txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).equals("Enter your password...") ? "" : new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter username and password", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AuthService authService = new AuthService();
            String role = authService.getRoleIfValid(username, password);
            int userId = authService.getUserId(username, password);

            if (role != null) {
                UserSession.setLoggedInUserId(userId);
                dispose();
                
                
                if (chkRemember.isSelected()) {
    config.setProperty("remember_username", username);
    config.setProperty("remember_password", password); // اختياري
    config.setProperty("remember_checked", "true");
} else {
    config.setProperty("remember_username", "");
    config.setProperty("remember_password", "");
    config.setProperty("remember_checked", "false");
}

config.save();
                
                
                

               
                switch (role) {
                    case "Manager":
                        new ManagerDashboard().setVisible(true);
                        break;
                    case "Department Manager":
                        new DepartmentManager().setVisible(true);
                        break;
                    case "Volunteer":
                        new VolunteerDashboard().setVisible(true);
                        break;
                    case "Employee":
                        new EmployeeDashboard().setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Role not recognized: " + role);
                        new EmployeeDashboard().setVisible(true);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
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

 
    class PlaceholderTextField extends JTextField {
        private String placeholder;
        public PlaceholderTextField(String placeholder) { this.placeholder = placeholder; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !hasFocus()) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(Color.GRAY);
                g2.drawString(placeholder, 10, 22);
            }
        }
        @Override protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        }
    }

    class PlaceholderPasswordField extends JPasswordField {
        private String placeholder;
        public PlaceholderPasswordField(String placeholder) { this.placeholder = placeholder; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0 && !hasFocus()) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(Color.GRAY);
                g2.drawString(placeholder, 10, 22);
            }
        }
        @Override protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        }
    }

    class RoundedButton extends JButton {
        private Color color;
        public RoundedButton(String text, Color color) {
            super(text); this.color = color; setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
            setForeground(Color.WHITE); setBackground(color);
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { setBackground(color.brighter()); repaint(); }
                @Override public void mouseExited(MouseEvent e) { setBackground(color); repaint(); }
            });
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            super.paintComponent(g2);
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