package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;
    private Budget budget;


    public ExpenseManager(Budget budget) {
        expenses = new ArrayList<>();
        this.budget = budget;
    }

    public void addExpenseToSubcategory(Expense expense) {
        Subcategory subcategory = expense.getSubcategory();
        subcategory.addExpense(expense);
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        addExpenseToSubcategory(expense);
        budget.increaseUsedBudget(expense.getAmount());
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
        budget.decreaseUsedBudget(expense.getAmount());
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public double getTotalExpensesForDay(LocalDate date) {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            if (expense.getDate().equals(date)) {
                totalExpenses += expense.getAmount();
            }
        }
        return totalExpenses;
    }

    public double getTotalExpensesForWeek(LocalDate startOfWeek, LocalDate endOfWeek) {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            LocalDate expenseDate = expense.getDate();
            if (expenseDate.isAfter(startOfWeek) || expenseDate.isEqual(startOfWeek)) {
                if (expenseDate.isBefore(endOfWeek) || expenseDate.isEqual(endOfWeek)) {
                    totalExpenses += expense.getAmount();
                }
            }
        }
        return totalExpenses;
    }

    public double getTotalExpensesForMonth(LocalDate month) {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            if (expense.getDate().getMonth().equals(month.getMonth())) {
                totalExpenses += expense.getAmount();
            }
        }
        return totalExpenses;
    }

    public double getTotalExpensesForYear(int year) {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            if (expense.getDate().getYear() == year) {
                totalExpenses += expense.getAmount();
            }
        }
        return totalExpenses;
    }
}
