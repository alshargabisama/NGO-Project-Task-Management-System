/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.view.VolunteerDashboard;

//import com.mycompany.tasktrakingsystem.view.VolunteerDashboard.VolunteerDashboard;
import javax.swing.SwingUtilities;

/**
 *
 * @author ENJAZ
 */
public class Volunteermain {
   public static void main(String[] args) {
   
        SwingUtilities.invokeLater(() -> {
            new VolunteerDashboard().setVisible(true);
    });
                }  
}
