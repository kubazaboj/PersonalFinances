package org.example;

import java.util.ArrayList;
import java.util.List;

public class IncomeManager {
    private List<Income> incomes;
    private Budget budget;

    public IncomeManager(Budget budget) {
        incomes = new ArrayList<>();
        this.budget = budget;
    }

    public void addIncomeToSubcategory(Income income, Subcategory subcategory) {
        subcategory.addIncome(income);
        income.setSubcategory(subcategory);
    }

    public void addIncome(Income income) {
        incomes.add(income);
        budget.increaseActualBudget(income.getAmount());
    }

    public void removeIncome(Income income) {
        incomes.remove(income);
        budget.decreaseActualBudget(income.getAmount());
    }

    public List<Income> getAllIncomes() {
        return incomes;
    }
}