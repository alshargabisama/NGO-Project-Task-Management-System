/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tasktrakingsystem;

/**
 *
 * @author ENJAZ
 */
//package com.mycompany.tasktrakingsystem;

import com.mycompany.tasktrakingsystem.util.DBConnection; // تأكدي من عمل Import
import java.sql.Connection;

public class TaskTrakingSystem {

    public static void main(String[] args) {
        // الاتصال بقاعدة البيانات
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Database connected");
            conn.close();
        } catch (Exception e) {
            System.out.println("database not connected");
            e.printStackTrace();
        }
    }
}

