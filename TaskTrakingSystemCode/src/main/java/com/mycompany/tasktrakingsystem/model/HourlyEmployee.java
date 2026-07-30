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

public class HourlyEmployee extends Employee {

    private double workingHours;
    private double hourlyRate;

    public HourlyEmployee(
            int id,
            String name,
            String email,
            String address,
            String phone,
            String gender,
            LocalDate dob,
            String status,
            String password,
            LocalDate hireDate,
            String jobTitle,
            String role,
            double workingHours,
            double hourlyRate) {

        super(id,name,email,address,
                phone,gender,dob,status,
                password,hireDate,
                jobTitle,role);

        this.workingHours = workingHours;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return workingHours * hourlyRate;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "salary=" + calculateSalary() +
                ", name=" + userName +
                '}';
    }
}
