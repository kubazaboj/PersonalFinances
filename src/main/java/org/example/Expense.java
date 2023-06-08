package org.example;

import java.time.LocalDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String description;
    private double amount;
    private LocalDate date;
    private Subcategory subcategory;

    public Expense(String description, double amount, LocalDate date, Subcategory subcategory) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.subcategory = subcategory;
        }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void removeSubcategory(){
        this.subcategory = null;
    }

    @Override
    public String toString() {
        String subcategoryString = (subcategory != null) ? subcategory.getName() : "N/A";

        return "Expense{" +
                "description ='" + description + '\'' +
                ", amount =" + amount +
                ", date =" + date +
                ", subcategory ='" + subcategoryString + '\'' +
                '}';
    }
}