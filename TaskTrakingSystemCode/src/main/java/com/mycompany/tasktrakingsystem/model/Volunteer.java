/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.model;

/**
 *
 * @author ENJAZ
 */

//package testtasktrackingsystemfororganization; 
import java.time.LocalDate; 
 
public class Volunteer extends Users { 
 
    private String skills; 
    private String volunteerStatus; 
 
    public Volunteer( 
            int id, 
            String name, 
            String email, 
            String address, 
            String phone, 
            String gender, 
            LocalDate dob, 
            String status, 
            String password, 
            String skills, 
            String volunteerStatus) { 
 
        super(id, name, email, address, 
                phone, gender, dob, 
                status, password); 
 
        this.skills = skills; 
        this.volunteerStatus = volunteerStatus; 
    } 
 
    public void updateTask(Task task) { 
        System.out.println("Volunteer updated task."); 
    } 
 
    public String getSkills() { 
        return skills; 
    } 
 
    public void setSkills(String skills) { 
        this.skills = skills; 
    } 
 
    public String getVolunteerStatus() { 
        return volunteerStatus; 
    } 
 
    public void setVolunteerStatus(String volunteerStatus) { 
        this.volunteerStatus = volunteerStatus; 
    } 
 
    @Override 
    public String toString() { 
        return "Volunteer{" + 
                "name='" + getUserName() + '\'' + 
                ", skills='" + skills + '\'' + 
                ", volunteerStatus='" + volunteerStatus + '\'' + 
                '}'; 
    } 
}