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


public class SalaryEmployee extends Employee {

    
    private double salary;

    
    public SalaryEmployee(int userId, String userName, String email, String address, 
                          String phone, String gender, LocalDate dateOfBirth, 
                          String status, String password, LocalDate hireDate, 
                          String jobTitle, String role, double salary) {
        
        
        super(userId, userName, email, address, phone, gender, dateOfBirth, status, password, hireDate, jobTitle, role);
        
        this.salary = salary;
    }

   
    @Override
    public double calculateSalary() {
        return this.salary; 
    }

    
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary >= 0) { 
            this.salary = salary;
        }
    }

    
    @Override
    public String toString() {
        return "SalaryEmployee{" +
                "name='" + getUserName() + '\'' +
                ", jobTitle='" + getJobTitle() + '\'' +
                ", fixedSalary=" + calculateSalary() +
                '}';
    }
}
