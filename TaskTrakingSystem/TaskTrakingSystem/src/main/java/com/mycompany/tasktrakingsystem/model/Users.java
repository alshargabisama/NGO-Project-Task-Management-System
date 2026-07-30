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
import java.time.Period; 
 
public class Users { 
 
    protected int userId; 
    protected String userName; 
    protected String email; 
    protected String address; 
    protected String phone; 
    protected String gender; 
    protected LocalDate dateOfBirth; 
    protected String status; 
    protected String password; 
 
    public Users() { 
    } 
 
    public Users(int userId, String userName, String email, 
                String address, String phone, 
                String gender, LocalDate dateOfBirth, 
                String status, String password) { 
 
        this.userId = userId; 
        this.userName = userName; 
        this.email = email; 
        this.address = address; 
        this.phone = phone; 
        this.gender = gender; 
        this.dateOfBirth = dateOfBirth; 
        this.status = status; 
        this.password = password; 
    } 
 
    public boolean login() { 
        return true; 
    } 
 
    public void logout() { 
        System.out.println(userName + " logged out."); 
    } 
 
    public void updateProfile() { 
        System.out.println("Profile updated."); 
    } 
 
    public int getAge() { 
        return Period.between(dateOfBirth, LocalDate.now()).getYears(); 
    } 
 
    public int getUserId() { 
        return userId; 
    } 
 
    public void setUserId(int userId) { 
        this.userId = userId; 
    } 
 
    public String getUserName() { 
        return userName; 
    } 
 
    public void setUserName(String userName) { 
        this.userName = userName; 
    } 
 
    @Override 
    public String toString() { 
        return "User{" + 
                "id=" + userId + 
                ", name='" + userName + '\'' + 
                ", email='" + email + '\'' + 
                '}'; 
    } 
}
