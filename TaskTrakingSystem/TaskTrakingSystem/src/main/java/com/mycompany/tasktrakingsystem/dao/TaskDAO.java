package com.mycompany.tasktrakingsystem.dao;

import com.mycompany.tasktrakingsystem.model.Task;
import com.mycompany.tasktrakingsystem.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
      
        String sql = "SELECT * FROM Task WHERE ASSIGNED_USER_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
              
                Task task = new Task();
                task.setTaskId(rs.getInt(1));
                task.setTaskTitle(rs.getString(3));
                task.setTaskDescription(rs.getString(4));
                task.setStatus(rs.getString(5));
                task.setStartDate(rs.getDate(6));
                task.setEndDate(rs.getDate(7));
                task.setCompletedAt(rs.getDate(8));
                
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return tasks;
    }


    public boolean updateTaskStatusAndDate(int taskId, String status, java.util.Date endDate) {
        String sql = "UPDATE Task SET STATUS = ?, END_DATE = ? WHERE TASK_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, status);
          
           
            pst.setDate(2, new java.sql.Date(endDate.getTime()));
            pst.setInt(3, taskId);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


