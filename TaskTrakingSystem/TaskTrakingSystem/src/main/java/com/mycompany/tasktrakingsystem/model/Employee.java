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
import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends Users {

    private LocalDate hireDate;
    protected String jobTitle;
    protected String role;

    private List<Task> tasks;

    public Employee(int userId, String userName, String email,
                    String address, String phone, String gender,
                    LocalDate dateOfBirth, String status,
                    String password, LocalDate hireDate,
                    String jobTitle, String role) {

        super(userId, userName, email, address, phone,gender, dateOfBirth, status, password);

        this.hireDate = hireDate;
        this.jobTitle = jobTitle;
        this.role = role;

        tasks = new ArrayList<>();
    }

    
    public abstract double calculateSalary();

    // Assign Task
    public void assignTask(Task task) {
        tasks.add(task);
        System.out.println("Task assigned successfully.");
    }

    // Update Task Status
    public void updateTaskStatus(Task task, String status) {
        task.updateStatus(status);
    }

    // Supervise Volunteer
    public void superviseVolunteer(Volunteer volunteer) {
        System.out.println(
                getUserName() + " supervises " +
                volunteer.getUserName()
        );
    }

    // Getters & Setters
    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
