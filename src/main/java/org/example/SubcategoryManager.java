package org.example;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryManager {
    private List<Subcategory> subcategories;

    public SubcategoryManager() {
        this.subcategories = new ArrayList<>();
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
        Category category = subcategory.getCategory();
        category.addSubcategory(subcategory);
    }

    public void removeSubcategory(Subcategory subcategory, ExpenseManager expenseManager) {
        // Remove expenses associated with the subcategory
        for (Expense expense : getAllSubcategoryExpenses(subcategory)) {
            expenseManager.removeExpense(expense);
            }
        Category category = subcategory.getCategory();
        category.removeSubcategory(subcategory);
        subcategories.remove(subcategory);
        }

    public List<Subcategory> getAllSubcategories() {
        return subcategories;
    }

    public Category getCategory(Subcategory subcategory) {
        for (Subcategory sub : subcategories) {
            if (sub.equals(subcategory)) {
                return sub.getCategory();
            }
        }
        return null;
    }

    public List<Expense> getAllSubcategoryExpenses(Subcategory subcategory) {
        List<Expense> allExpenses = new ArrayList<>();
        collectExpenses(subcategory, allExpenses);
        return allExpenses;
    }

    private void collectExpenses(Subcategory subcategory, List<Expense> allExpenses) {
        allExpenses.addAll(subcategory.getExpenses());

        for (Subcategory sub : getSubcategories()) {
            collectExpenses(sub, allExpenses);
        }
    }

    public List<Expense> getAllSubcategoryIncomes(Subcategory subcategory) {
        List<Expense> allExpenses = new ArrayList<>();
        collectExpenses(subcategory, allExpenses);
        return allExpenses;
    }

    private void collectIncomes(Subcategory subcategory, List<Expense> allExpenses) {
        allExpenses.addAll(subcategory.getExpenses());

        for (Subcategory sub : getSubcategories()) {
            collectExpenses(sub, allExpenses);
        }
    }

}
