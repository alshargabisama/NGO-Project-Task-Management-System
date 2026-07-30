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

public class Sponsor {

    private int sponsorId;
    private String sponsorName;
    private String sponsorType;
    private String email;
    private String phone;
    private String address;
    private String website;
    private String contactPerson;

    public Sponsor(int sponsorId,
                   String sponsorName,
                   String sponsorType,
                   String email,
                   String phone,
                   String address,
                   String website,
                   String contactPerson) {

        this.sponsorId = sponsorId;
        this.sponsorName = sponsorName;
        this.sponsorType = sponsorType;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.website = website;
        this.contactPerson = contactPerson;
    }

    public String getSponsorInfo(Project p) {

        return "Sponsor ID: " + sponsorId +
               "\nSponsor Name: " + sponsorName +
               "\nSponsor Type: " + sponsorType +
               "\nProject: " + p.getProjName();
    }

    public void createFunding(Project p) {

        System.out.println(
                sponsorName +
                " created funding for project: " +
                p.getProjName()
        );
    }


    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getSponsorType() {
        return sponsorType;
    }

    public void setSponsorType(String sponsorType) {
        this.sponsorType = sponsorType;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}
