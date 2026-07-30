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
import java.util.ArrayList;
import java.util.List;
public class Department {

    
    private int depId;
    private String depName;
    private String depDescription;

  
    private Employee manager;
    private List<Employee> employees;

    public Department(int depId, String depName) {

        this.depId = depId;
        this.depName = depName;
        this.depDescription = depDescription;

        employees = new ArrayList<>();
    }

   
    public void addEmployee(Employee e) {
        if (e != null && !employees.contains(e)) {
            employees.add(e);
        }
    }

    // Remove Employee
    public void removeEmployee(Employee e) {
        employees.remove(e);
    }

    public void assignManager(Employee e) {

        if (e != null) {

            if (!employees.contains(e)) {
                employees.add(e);
            }

            manager = e;
        }
    }

 

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepDescription() {
        return depDescription;
    }

    public void setDepDescription(String depDescription) {
        this.depDescription = depDescription;
    }

    public Employee getManager() {
        return manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}