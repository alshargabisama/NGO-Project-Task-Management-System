/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tasktrakingsystem.model;

//import java.time.LocalDate;

/**
 *
 * @author ENJAZ
 */
//package testtasktrackingsystemfororganization; 
import java.time.LocalDate; 
 
public class Works_On { 
 
    private Employee employee; 
    private Project project; 
    private LocalDate assignedDate; 
 
    public Works_On(Employee employee, 
                   Project project, 
                   LocalDate assignedDate) { 
 
        this.employee = employee; 
        this.project = project; 
        this.assignedDate = assignedDate; 
    } 
 
    public Employee getEmployee() { 
        return employee; 
    } 
 
    public Project getProject() { 
        return project; 
    } 
 
    public LocalDate getAssignedDate() { 
        return assignedDate; 
    } 
 
    public void setAssignedDate(LocalDate assignedDate) { 
        this.assignedDate = assignedDate; 
    } 
 
    @Override 
    public String toString() { 
        return "Employee = " + employee.getUserName() 
                + ", Project = " + project.getProjName() 
                + ", Assigned Date = " + assignedDate; 
    } 
}
