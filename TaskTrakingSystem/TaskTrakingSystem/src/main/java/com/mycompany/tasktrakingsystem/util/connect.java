/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.util;

import java.sql.DriverManager;

/**
 *
 * @author ENJAZ
 */
public class connect {
     public static void main(String[] args) {

    try {
       
        java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "C##TaskSystem", "123456");
        if (conn != null) {
            System.out.println("تم الاتصال بنجاح بقاعدة البياناتaaa!");
        }
    } catch (java.sql.SQLException e) {
        System.out.println("فشل الاتصال! ccccالسبب: " + e.getMessage());
    }
}
    
}
