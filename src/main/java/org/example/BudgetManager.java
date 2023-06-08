package org.example;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private List<Budget> budgets;

    public BudgetManager() {
        this.budgets = new ArrayList<>();
    }

    public void addBudget(Budget budget) {
        budgets.add(budget);
    }

    public void removeBudget(Budget budget) {
        budgets.remove(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgets;
    }

    public double getRemainingBudget(Budget budget) {
        return budget.getAllocatedBudget() - budget.getUsedBudget();
    }

    public Budget getBudgetByYearMonth(YearMonth yearMonth) {
        for (Budget budget : budgets) {
            if (budget.getYearMonth().equals(yearMonth)) {
                return budget;
            }
        }
        return null;
    }

    public boolean isBudgetOverdraftedForMonth(YearMonth yearMonth) {
        Budget budget = getBudgetByYearMonth(yearMonth);
        if (budget != null) {
            return budget.getUsedBudget() > budget.getAllocatedBudget();
        }
        return false;
    }

    public List<YearMonth> getOverdraftedMonthsForYear(int year) {
        List<YearMonth> overdraftedMonths = new ArrayList<>();
        for (Budget budget : budgets) {
            if (budget.getYearMonth().getYear() == year && budget.getUsedBudget() > budget.getAllocatedBudget()) {
                overdraftedMonths.add(budget.getYearMonth());
            }
        }
        return overdraftedMonths;
    }
}
