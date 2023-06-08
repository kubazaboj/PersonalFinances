package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class IntegrationTest {
    private BudgetManager budgetManager;
    private CategoryManager categoryManager;
    private SubcategoryManager subcategoryManager;
    private ExpenseManager expenseManager;
    private IncomeManager incomeManager;

    private Budget budget;

    @BeforeEach
    public void beforeEach() {
        // Initialize managers
        budgetManager = new BudgetManager();
        categoryManager = new CategoryManager();
        subcategoryManager = new SubcategoryManager();
        budget = new Budget(YearMonth.now());
        expenseManager = new ExpenseManager(budget);
        incomeManager = new IncomeManager(budget);
    }

    @Test
    public void testExpenseAndIncomeManagement() {
        // Create a budget
        budgetManager.addBudget(budget);

        // Create a category for an expense
        Category categoryFood = new Category("Food");
        categoryManager.addCategory(categoryFood);

        // Create a subcategory for an expense
        Subcategory subcategoryGroceries = new Subcategory("Groceries", categoryFood);
        subcategoryManager.addSubcategory(subcategoryGroceries);

        // Create an expense
        Expense expense = new Expense("Milk", 5.99, LocalDate.now(), subcategoryGroceries);
        expenseManager.addExpense(expense);

        // Create a category for an income
        Category categoryWork = new Category("Work");
        categoryManager.addCategory(categoryWork);

        // Create a subcategory for an income
        Subcategory subcategorySalaries = new Subcategory("Salaries", categoryWork);
        subcategoryManager.addSubcategory(subcategorySalaries);

        // Create an income
        Income income = new Income("Salary", 2000, LocalDate.now(), subcategorySalaries);
        incomeManager.addIncome(income);

        // Verify the expense and income amounts
        Assertions.assertEquals(5.99, expenseManager.getTotalExpenses());
        Assertions.assertEquals(2000, incomeManager.getTotalIncome());

        // Verify the remaining budget
        System.out.println(budget.getAllocatedBudget());
        System.out.println(budget.getUsedBudget());
        Assertions.assertEquals(1994.01, budgetManager.getRemainingBudget(budget));

        // Verify the allocated and actual budgets in the budget
        Assertions.assertEquals(2000, budget.getAllocatedBudget());
        Assertions.assertEquals(5.99, budget.getUsedBudget());

        // Verify the subcategories in the category
        List<Subcategory> subcategories = categoryManager.getAllCategorySubcategories(categoryFood);
        Assertions.assertEquals(1, subcategories.size());
        Assertions.assertEquals(subcategoryGroceries, subcategories.get(0));
    }

    @Test
    public void testBudgetManagement() {
        // Create budgets for different months
        YearMonth currentYearMonth = YearMonth.now();
        LocalDate today = LocalDate.now();
        Budget budget2 = new Budget(currentYearMonth.plusMonths(1));
        budgetManager.addBudget(budget);
        budgetManager.addBudget(budget2);

        // Increase the actual budget for budget1
        budget.increaseUsedBudget(500);
        budget.increaseAllocatedBudget(1000);

        // Increase the actual budget for budget2
        budget2.increaseUsedBudget(2500);

        // Verify the days left in the budgets
        Assertions.assertEquals(ChronoUnit.DAYS.between(today, today.plusMonths(1).withDayOfMonth(1)) - 1, budget.getDaysLeftInBudget());
        //Subtracting -1 because the last day has 0 days remaining, only several hours

        Assertions.assertEquals(ChronoUnit.DAYS.between(today, today.plusMonths(2).withDayOfMonth(1)) - 1, budget2.getDaysLeftInBudget());
        //Same situation, subtracting -1 because the last day has 0 days remaining, only several hours

        // Verify the spending percentage of budget1
        Assertions.assertEquals(50, budget.getSpendingPercentage());

        // Verify if budget1 is overdrafted
        Assertions.assertFalse(budget.isBudgetOverdrafted());
        Assertions.assertTrue(budget2.isBudgetOverdrafted());

        // Remove budget1
        budgetManager.removeBudget(budget);

        // Verify if budget1 is removed
        Assertions.assertNull(budgetManager.getBudgetByYearMonth(currentYearMonth));
    }

    @Test
    public void testCategoryManagement() {
        // Create categories
        Category category1 = new Category("Food");
        Category category2 = new Category("Transportation");
        categoryManager.addCategory(category1);
        categoryManager.addCategory(category2);

        // Verify the total number of categories
        Assertions.assertEquals(2, categoryManager.getAllCategories().size());

        // Remove category1
        categoryManager.removeCategory(category1, subcategoryManager, expenseManager);

        // Verify the total number of categories after removal
        Assertions.assertEquals(1, categoryManager.getAllCategories().size());

        // Verify if category1 is removed
        Assertions.assertFalse(categoryManager.getAllCategories().contains(category1));
    }
}