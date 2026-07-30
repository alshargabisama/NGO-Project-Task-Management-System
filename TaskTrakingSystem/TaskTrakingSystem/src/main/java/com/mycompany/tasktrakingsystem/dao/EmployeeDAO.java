package com.mycompany.tasktrakingsystem.dao;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.model.Employee;
import com.mycompany.tasktrakingsystem.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private static class ConcreteEmployee extends Employee {
        public ConcreteEmployee(int userId, String userName, String email, String address, String phone, 
                                String gender, LocalDate dateOfBirth, String status, String password, 
                                LocalDate hireDate, String jobTitle, String role) {
            super(userId, userName, email, address, phone, gender, dateOfBirth, status, password, hireDate, jobTitle, role);
        }

        @Override
        public double calculateSalary() {
            return 0.0;
        }
    }

 
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        
      
        String query = "SELECT u.USER_ID, u.USER_NAME, u.EMAIL, u.ADDRESS, u.PHONE, u.GENDER, " +
                       "e.HIRE_DATE, e.JOB_TITLE, e.EMP_ROLE " +
                       "FROM USERS u JOIN EMPLOYEE e ON u.USER_ID = e.USER_ID ORDER BY u.USER_ID";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
             
                int userId = rs.getInt(1);        
                String userName = rs.getString(2);  
                String email = rs.getString(3);      
                String address = rs.getString(4);    
                String phone = rs.getString(5);      
                String gender = rs.getString(6);     
                
                
                LocalDate hireDate = null;
                Date hDate = rs.getDate(7);         
                if (hDate != null) {
                    hireDate = hDate.toLocalDate();
                }
                
                String jobTitle = rs.getString(8);   
                String role = rs.getString(9);      
                
               
                Employee emp = new ConcreteEmployee(userId, userName, email, address, phone, gender, 
                                                    LocalDate.of(2000, 1, 1), "Active", "pass123", 
                                                    hireDate, jobTitle, role);
                list.add(emp);
            }
            System.out.println("Successfully loaded employees dynamically! Total rows: " + list.size());
            
        } catch (SQLException e) {
            System.err.println("Database Error: Failed to fetch employees using secure column index.");
            e.printStackTrace();
        }
        return list;
    }

  
    public boolean addEmployee(Employee emp) {
        String insertUserSql = "INSERT INTO USERS (USER_ID, USER_NAME, EMAIL, ADDRESS, PHONE, GENDER, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?)";

        String insertEmpSql = "INSERT INTO EMPLOYEE (USER_ID, HIRE_DATE, JOB_TITLE, SUPER_USER_ID, EMP_ROLE) VALUES (?, ?, ?, 1, ?)";
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 
            
            try (PreparedStatement pstUser = conn.prepareStatement(insertUserSql)) {
                pstUser.setInt(1, emp.getUserId());
                pstUser.setString(2, emp.getUserName());
                pstUser.setString(3, (emp.getUserName().toLowerCase() + "@org.com")); 
                pstUser.setString(4, "Taiz");
                pstUser.setString(5, "000000");
                pstUser.setString(6, "Male");
                pstUser.setString(7, "pass123");
                pstUser.executeUpdate();
            }
            
            try (PreparedStatement pstEmp = conn.prepareStatement(insertEmpSql)) {
                pstEmp.setInt(1, emp.getUserId());
                pstEmp.setDate(2, Date.valueOf(LocalDate.now())); 
                pstEmp.setString(3, emp.getJobTitle());
                pstEmp.setString(4, emp.getRole());
                pstEmp.executeUpdate();
            }
            
            conn.commit(); 
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Database Error: Failed to insert employee records.");
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

   
    public boolean updateEmployee(Employee emp) {
        String updateUserSql = "UPDATE USERS SET USER_NAME=? WHERE USER_ID=?";
        String updateEmpSql = "UPDATE EMPLOYEE SET JOB_TITLE=?, EMP_ROLE=? WHERE USER_ID=?";
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstUser = conn.prepareStatement(updateUserSql)) {
                pstUser.setString(1, emp.getUserName());
                pstUser.setInt(2, emp.getUserId());
                pstUser.executeUpdate();
            }
            
            try (PreparedStatement pstEmp = conn.prepareStatement(updateEmpSql)) {
                pstEmp.setString(1, emp.getJobTitle());
                pstEmp.setString(2, emp.getRole());
                pstEmp.setInt(3, emp.getUserId());
                pstEmp.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Database Error: Failed to update employee.");
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public boolean deleteEmployee(int id) {
        String deleteEmpSql = "DELETE FROM EMPLOYEE WHERE USER_ID = ?";
        String deleteUserSql = "DELETE FROM USERS WHERE USER_ID = ?";
        
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstEmp = conn.prepareStatement(deleteEmpSql)) {
                pstEmp.setInt(1, id);
                pstEmp.executeUpdate();
            }
            
            try (PreparedStatement pstUser = conn.prepareStatement(deleteUserSql)) {
                pstUser.setInt(1, id);
                int rows = pstUser.executeUpdate();
                conn.commit();
                return rows > 0;
            }
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Database Error: Failed to delete employee.");
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}