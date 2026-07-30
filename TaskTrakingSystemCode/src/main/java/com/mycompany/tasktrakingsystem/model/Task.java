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
import java.util.Date; 
public class Task { 
 
    private int taskId; 
    private String taskTitle; 
    private String taskDescription; 
    private String status; 
    private Date startDate; 
    private Date endDate; 
    private Date completedAt; 
 
    public Task() { 
    } 
 
    public Task(int taskId, 
                String taskTitle, 
                String taskDescription, 
                String status, 
                Date startDate, 
                Date endDate, 
                Date completedAt) { 
 
        this.taskId = taskId; 
        this.taskTitle = taskTitle; 
        this.taskDescription = taskDescription; 
        this.status = status; 
        this.startDate = startDate; 
        this.endDate = endDate; 
        this.completedAt = completedAt; 
    } 
 
    public void updateStatus(String status) { 
        this.status = status; 
    } 
 
    public void addDescription(String desc) { 
        this.taskDescription = desc; 
    } 
 
    public int getTaskId() { 
        return taskId; 
    } 
 
    public void setTaskId(int taskId) { 
        this.taskId = taskId; 
    } 
 
    public String getTaskTitle() { 
        return taskTitle; 
    } 
 
    public void setTaskTitle(String taskTitle) { 
        this.taskTitle = taskTitle; 
    } 
 
    public String getTaskDescription() { 
        return taskDescription; 
    } 
 
    public void setTaskDescription(String taskDescription) { 
        this.taskDescription = taskDescription; 
    } 
 
    public String getStatus() { 
        return status; 
    } 
 
    public void setStatus(String status) { 
        this.status = status; 
    } 
 
    public Date getStartDate() { 
        return startDate; 
    } 
 
    public void setStartDate(Date startDate) { 
        this.startDate = startDate; 
    } 
 
    public Date getEndDate() { 
        return endDate; 
    } 
 
    public void setEndDate(Date endDate) { 
        this.endDate = endDate; 
    } 
 
    public Date getCompletedAt() { 
        return completedAt; 
    } 
 
    public void setCompletedAt(Date completedAt) { 
        this.completedAt = completedAt; 
    } 
 
    @Override 
    public String toString() { 
        return "Task{" + 
                "taskId=" + taskId + 
                ", taskTitle='" + taskTitle + '\'' + 
                ", status='" + status + '\'' + 
                '}'; 
    } 
}