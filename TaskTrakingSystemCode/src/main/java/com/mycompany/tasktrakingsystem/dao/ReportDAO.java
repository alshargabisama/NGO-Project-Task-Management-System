package com.mycompany.tasktrakingsystem.dao;

/**
 *
 * @author ENJAZ
 */

import com.mycompany.tasktrakingsystem.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

  
    public List<Object[]> getProjectDetailedReport() {
        List<Object[]> list = new ArrayList<>();
        String query = "SELECT * FROM PROJECT_DETAILED_REPORT";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int projectId = rs.getInt(1);         
                String projectName = rs.getString(2);  
                String projectStatus = rs.getString(3);  
                int taskId = rs.getInt(6);             
                String taskName = rs.getString(7);      
                String taskStatus = rs.getString(8);   
                
                if (taskName == null || taskName.trim().isEmpty()) {
                    taskName = "No Assigned Tasks";
                }
                if (taskStatus == null || taskStatus.trim().isEmpty()) {
                    taskStatus = "-";
                }

                list.add(new Object[]{
                    projectId, projectName, projectStatus, taskId, taskName, taskStatus
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getAllProjectIds() {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT DISTINCT PROJECT_ID FROM PROJECT_DETAILED_REPORT ORDER BY PROJECT_ID";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                ids.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Database Error: Failed to fetch distinct project IDs.");
            e.printStackTrace();
        }
        return ids;
    }
}