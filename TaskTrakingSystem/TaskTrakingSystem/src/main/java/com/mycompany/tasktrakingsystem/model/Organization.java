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

import java.util.ArrayList;

public class Organization {

    
    private int orgId;
    private String orgName;
    private String orgType;
    private String email;
    private String phone;
    private String address;
    private String website;


    private List<Department> departments;

  
    public Organization(int orgId, String orgName, String orgType,
                        String email, String phone,
                        String address, String website) {

        this.orgId = orgId;
        this.orgName = orgName;
        this.orgType = orgType;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.website = website;

        departments = new ArrayList<>();
    }

    public void addDepartment(Department d) {
        if (d != null) {
            departments.add(d);
        }
    }

 
    public void removeDepartment(Department d) {
        departments.remove(d);
    }

   

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
