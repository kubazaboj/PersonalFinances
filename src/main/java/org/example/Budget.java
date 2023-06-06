package org.example;

import java.time.LocalDate;
import java.time.YearMonth;

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

    @Override
    public String toString() {
        return "Budget{" +
                "setBudget=" + actualBudget +
                ", allocatedBudget=" + allocatedBudget +
                ", yearMonth=" + yearMonth +
                '}';
    }
}