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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Project {

    
    private int projId;
    private String projName;
    private String projDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

   
    private List<Task> tasks;
    private List<Volunteer> volunteers;
    private List<Works_On> worksOnList;

  
    public Project(int projId,
                   String projName,
                   String projDescription,
                   LocalDate startDate,
                   LocalDate endDate,
                   String status) {

        this.projId = projId;
        this.projName = projName;
        this.projDescription = projDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;

        tasks = new ArrayList<>();
        volunteers = new ArrayList<>();
        worksOnList = new ArrayList<>();
    }

    // Add Task
    public void addTask(Task t) {
        if (t != null) {
            tasks.add(t);
        }
    }

    // Assign Employee to Project
    public void assignEmployee(Employee e) {

        if (e != null) {

            Works_On work = new Works_On(
                    e,
                    this,
                    LocalDate.now()
            );

            worksOnList.add(work);
        }
    }

    // Assign Volunteer
    public void assignVolunteer(Volunteer v) {

        if (v != null && !volunteers.contains(v)) {
            volunteers.add(v);
        }
    }

    // Calculate Progress
    public double getProgress() {

        if (tasks.isEmpty()) {
            return 0;
        }

        int completedTasks = 0;

        for (Task task : tasks) {

            if ("Completed".equalsIgnoreCase(task.getStatus())) {
                completedTasks++;
            }
        }

        return (completedTasks * 100.0) / tasks.size();
    }

    
    public void getProjectTeam() {

        System.out.println("===== Employees =====");

        for (Works_On work : worksOnList) {

            System.out.println(
                    work.getEmployee().getUserName()
            );
        }

        System.out.println("\n===== Volunteers =====");

        for (Volunteer volunteer : volunteers) {

            System.out.println(
                    volunteer.getUserName()
            );
        }
    }

   
    public void generateReport() {

        System.out.println("\n===== Project Report =====");
        System.out.println("Project ID: " + projId);
        System.out.println("Project Name: " + projName);
        System.out.println("Status: " + status);
        System.out.println("Duration: " + getDuration() + " days");
        System.out.println("Progress: " + getProgress() + "%");
        System.out.println("Tasks: " + tasks.size());
        System.out.println("Employees: " + worksOnList.size());
        System.out.println("Volunteers: " + volunteers.size());
    }

    public int getDuration() {

        return (int) ChronoUnit.DAYS.between(
                startDate,
                endDate
        );
    }

 

    public int getProjId() {
        return projId;
    }

    public String getProjName() {
        return projName;
    }

    public String getProjDescription() {
        return projDescription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public List<Works_On> getWorksOnList() {
        return worksOnList;
    }

    

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public void setProjDescription(String projDescription) {
        this.projDescription = projDescription;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}