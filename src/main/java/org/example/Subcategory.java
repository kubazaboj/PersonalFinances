package org.example;
import java.util.ArrayList;
import java.util.List;

public class Subcategory {
    private String name;
    private Category category;
    private List<Expense> expenses;
    private List<Subcategory> subcategories;

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
        this.expenses = new ArrayList<>();
        this.subcategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void addSubcategory(Subcategory subcategory) {
        subcategories.add(subcategory);
    }

    public void removeSubcategory(Subcategory subcategory) {
        subcategories.remove(subcategory);
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "name ='" + name + '\'' +
                ", category =" + category.getName() +
                ", expenses =" + expenses +
                '}';
    }
}
