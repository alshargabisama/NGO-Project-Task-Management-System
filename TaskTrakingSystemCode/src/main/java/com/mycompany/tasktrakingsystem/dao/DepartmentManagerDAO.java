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

public class DepartmentManagerDAO {

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String query = "SELECT dept_id, dept_name FROM departments ORDER BY dept_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Department dept = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
                list.add(dept);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addDepartment(int id, String name) {
        String query = "INSERT INTO departments (dept_id, dept_name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) { 
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            return pstmt.executeUpdate() > 0; 
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean updateDepartment(int id, String name) {
        String query = "UPDATE departments SET dept_name = ? WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteDepartment(int id) {
        String query = "DELETE FROM departments WHERE dept_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }


    public List<String> getAllAssignees() {
        List<String> names = new ArrayList<>();
        String sql = "SELECT USER_NAME FROM Users u JOIN Employee e ON u.USER_ID = e.USER_ID " +
                     "UNION " +
                     "SELECT USER_NAME FROM Users u JOIN Volunteer v ON u.USER_ID = v.USER_ID";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                names.add(rs.getString("USER_NAME"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return names;
    }

    public List<Object[]> getAllProjectData() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT p.PROJ_NAME, p.PROJ_DESCRIPTION, p.START_DATE, p.END_DATE, p.STATUS, " +
                     "t.TASK_TITLE, t.TASK_DESCRIPTION, t.START_DATE, t.END_DATE, u.USER_NAME, t.STATUS " +
                     "FROM Project p " +
                     "LEFT JOIN Task t ON p.PROJ_ID = t.PROJECT_ID " +
                     "LEFT JOIN Users u ON t.ASSIGNED_USER_ID = u.USER_ID";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5),
                    rs.getString(6), rs.getString(7), rs.getDate(8), rs.getDate(9), rs.getString(10), rs.getString(11)
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    
    public void addProject(int projId, String name, String desc, String start, String end, String status) {
        String sql = "INSERT INTO Project (PROJ_ID, PROJ_NAME, PROJ_DESCRIPTION, START_DATE, END_DATE, STATUS) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, projId); 
            pst.setString(2, name);
            pst.setString(3, desc);
            pst.setString(4, start);
            pst.setString(5, end);
            pst.setString(6, status);
            pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

   
    public void addTask(int projId, String title, String desc, String start, String end, String status) {
        int nextTaskId = getNextId("Task", "TASK_ID");
        String sql = "INSERT INTO Task (TASK_ID, PROJECT_ID, TASK_TITLE, TASK_DESCRIPTION, START_DATE, END_DATE, STATUS) VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, nextTaskId);
            pst.setInt(2, projId);
            pst.setString(3, title);
            pst.setString(4, desc);
            pst.setString(5, start);
            pst.setString(6, end);
            pst.setString(7, status);
            pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public int getNextId(String tableName, String idColumn) {
        String sql = "SELECT MAX(" + idColumn + ") FROM " + tableName;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) + 1; 
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 1; 
    }

    public void deleteProject(String name) {
        String sql = "DELETE FROM Project WHERE PROJ_NAME = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }


    public void deleteTask(String taskTitle) {
        String sql = "DELETE FROM Task WHERE TASK_TITLE = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, taskTitle);
            pst.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}