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

public class Funding {

    private Sponsor sponsor;
    private Project project;

    private double amount;
    private String currency;
    private LocalDate agreementDate;
    private String notes;

    public Funding(Sponsor sponsor,
                   Project project,
                   double amount,
                   String currency,
                   LocalDate agreementDate,
                   String notes) {

        this.sponsor = sponsor;
        this.project = project;
        this.amount = amount;
        this.currency = currency;
        this.agreementDate = agreementDate;
        this.notes = notes;
    }

    public void addFunding() {
        System.out.println("Funding added successfully.");
    }

    public String getFundingDetails() {

        return "Sponsor: " + sponsor.getSponsorName()
                + "\nProject: " + project.getProjName()
                + "\nAmount: " + amount
                + "\nCurrency: " + currency
                + "\nAgreement Date: " + agreementDate
                + "\nNotes: " + notes;
    }

  

    public Sponsor getSponsor() {
        return sponsor;
    }

    public Project getProject() {
        return project;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public String getNotes() {
        return notes;
    }

    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}