package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class Budget {
    private double usedBudget;
    private double allocatedBudget;
    private YearMonth yearMonth;

    public Budget(YearMonth yearMonth) {
        this.usedBudget = 0;
        this.allocatedBudget = 0;
        this.yearMonth = yearMonth;
    }

    public double getUsedBudget() {
        return usedBudget;
    }

    public double getAllocatedBudget() {
        return allocatedBudget;
    }

    public void setAllocatedBudget(double allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
    }

    public void increaseUsedBudget(double amount) {
        this.usedBudget += amount;
    }

    public void decreaseAllocatedBudget(double amount) {
        this.allocatedBudget -= amount;
    }

    public void increaseAllocatedBudget(double amount) {
        this.allocatedBudget += amount;
    }

    public void decreaseUsedBudget(double amount) {
        this.usedBudget -= amount;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public long getDaysLeftInBudget() {
        YearMonth currentYearMonth = YearMonth.now();
        if (currentYearMonth.isAfter(yearMonth)) {
            System.out.println("This budget's deadline has already passed.");
            return 0;
        } else {
            return ChronoUnit.DAYS.between(LocalDate.now(), yearMonth.atEndOfMonth());
        }
    }

    public double getSpendingPercentage() {
        if (allocatedBudget == 0) {
            return 0;
        } else {
            return (usedBudget / allocatedBudget) * 100;
        }
    }

    public boolean isBudgetOverdrafted() {
        return usedBudget > allocatedBudget;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "setBudget=" + usedBudget +
                ", allocatedBudget=" + allocatedBudget +
                ", yearMonth=" + yearMonth +
                '}';
    }
}