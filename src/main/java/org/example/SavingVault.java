package org.example;

public class SavingVault {
    private String dream;
    private double savings;
    private double dreamAmount;
    public SavingVault(String dream, double dreamAmount) {
        this.dream = dream;
        this.savings = 0.0;
        this.dreamAmount = dreamAmount;
    }

    public String getDream() {
        return dream;
    }

    public double getSavings() {
        return savings;
    }

    public double getDreamAmount() {
        return dreamAmount;
    }
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        savings += amount;
        if (savings >= dreamAmount){
            System.out.println("Money for dream fulfilled!");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (amount > savings) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        savings -= amount;
    }

}