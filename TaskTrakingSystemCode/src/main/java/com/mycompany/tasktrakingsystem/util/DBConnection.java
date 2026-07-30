/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.util;

/**
 *
 * @author ENJAZ
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    
public static Connection getConnection() throws SQLException {
   
    String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
    
    String user = "C##TaskSystem";
    String password = "123456";
    
    return DriverManager.getConnection(url, user, password);
}}


