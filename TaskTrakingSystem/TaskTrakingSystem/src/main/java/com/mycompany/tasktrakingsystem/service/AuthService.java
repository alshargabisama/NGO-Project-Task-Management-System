package com.mycompany.tasktrakingsystem.service;

import com.mycompany.tasktrakingsystem.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());

   
public String getRoleIfValid(String username, String password) {
    String role = null;
    try (Connection conn = DBConnection.getConnection()) {
      
        String sqlEmp = "SELECT e.EMP_ROLE FROM Employee e JOIN Users u ON e.USER_ID = u.USER_ID " +
                        "WHERE u.USER_NAME = ? AND u.PASSWORD = ?";
        
        PreparedStatement pst = conn.prepareStatement(sqlEmp);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            return rs.getString("EMP_ROLE");
        } 
       
        String sqlVol = "SELECT v.USER_ID FROM Volunteer v JOIN Users u ON v.USER_ID = u.USER_ID " +
                        "WHERE u.USER_NAME = ? AND u.PASSWORD = ?";
        
        PreparedStatement pstVol = conn.prepareStatement(sqlVol);
        pstVol.setString(1, username);
        pstVol.setString(2, password);
        ResultSet rsVol = pstVol.executeQuery();
        
        if (rsVol.next()) {
            return "Volunteer"; 
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
   
    public int getUserId(String username, String password) {
        String sql = "SELECT User_id FROM Users WHERE User_name = ? AND password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("User_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error during getUserId", e);
        }
        return 0; 
    }
}