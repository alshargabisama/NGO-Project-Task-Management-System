package com.mycompany.tasktrakingsystem.dao;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.model.Department;
import com.mycompany.tasktrakingsystem.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

   
    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
       
        String query = "SELECT DEP_ID, ORG_ID, DEP_NAME FROM DEPARTMENT ORDER BY DEP_ID";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
              
                int id = rs.getInt(1);       
                String name = rs.getString(3);   
                
              
                Department dept = new Department(id, name);
                list.add(dept);
            }
            
            System.out.println("Successfully loaded departments! Total rows: " + list.size());
            
        } catch (SQLException e) {
            System.err.println("Database Error: Failed to fetch departments using column index.");
            e.printStackTrace();
        }
        return list;
    }

   
    public boolean addDepartment(Department dept) {
       
        String query = "INSERT INTO DEPARTMENT (DEP_ID, DEP_NAME, ORG_ID, MGR_USER_ID) VALUES (?, ?, 1, 1)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) { 
            
          
            pstmt.setInt(1, dept.getDepId());
            pstmt.setString(2, dept.getDepName());
            
            return pstmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            System.err.println("Database Error: Failed to insert department.");
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean updateDepartment(Department dept) {
        String query = "UPDATE DEPARTMENT SET DEP_NAME = ? WHERE DEP_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, dept.getDepName());
            pstmt.setInt(2, dept.getDepId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Database Error: Failed to update department.");
            e.printStackTrace();
            return false;
        }
    }

  
    public boolean deleteDepartment(int id) {
        String query = "DELETE FROM DEPARTMENT WHERE DEP_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Database Error: Failed to delete department.");
            e.printStackTrace();
            return false;
        }
    }
}