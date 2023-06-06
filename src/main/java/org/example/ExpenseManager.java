package org.example;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;
    private Budget budget;


    public ExpenseManager(Budget budget) {
        expenses = new ArrayList<>();
        this.budget = budget;
    }

    public void addExpenseToSubcategory(Expense expense, Subcategory subcategory) {
        subcategory.addExpense(expense);
        expense.setSubcategory(subcategory);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        budget.increaseActualBudget(expense.getAmount());
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
        budget.decreaseActualBudget(expense.getAmount());
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }
}
