package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public class Budget {
    private double actualBudget;
    private double allocatedBudget;
    private YearMonth yearMonth;

    public Budget(double allocatedBudget, YearMonth yearMonth) {
        this.actualBudget = 0;
        this.allocatedBudget = allocatedBudget;
        this.yearMonth = yearMonth;
    }

    public double getActualBudget() {
        return actualBudget;
    }

    public double getAllocatedBudget() {
        return allocatedBudget;
    }

    public void setAllocatedBudget(double allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
    }

    public void increaseActualBudget(double amount) {
        this.actualBudget += amount;
    }

    public void decreaseAllocatedBudget(double amount) {
        this.allocatedBudget -= amount;
    }

    public void increaseAllocatedBudget(double amount) {
        this.allocatedBudget += amount;
    }

    public void decreaseActualBudget(double amount) {
        this.actualBudget -= amount;
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
            return (actualBudget / allocatedBudget) * 100;
        }
    }

    public boolean isBudgetOverdrafted() {
        return actualBudget > allocatedBudget;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "setBudget=" + actualBudget +
                ", allocatedBudget=" + allocatedBudget +
                ", yearMonth=" + yearMonth +
                '}';
    }
}