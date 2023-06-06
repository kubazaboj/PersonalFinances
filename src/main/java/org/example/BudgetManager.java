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

    public Budget getBudgetByYearMonth(YearMonth yearMonth) {
        for (Budget budget : budgets) {
            if (budget.getYearMonth().equals(yearMonth)) {
                return budget;
            }
        }
        return null;
    }
}
