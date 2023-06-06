package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerIntegrationTest {


    private ExpenseManager expenseManager = new ExpenseManager(new Budget(10, YearMonth.now()));;
    private CategoryManager categoryManager = new CategoryManager();
    private SubcategoryManager subcategoryManager = new SubcategoryManager();


    @Test
    public void testExpenseManagementScenario() {
        // Create categories
        Category foodCategory = new Category("Food");
        Category travelCategory = new Category("Travel");

        // Create subcategories
        Subcategory fastFoodSubcategory = new Subcategory("Fast Food", foodCategory);
        Subcategory restaurantsSubcategory = new Subcategory("Restaurants", foodCategory);
        Subcategory flightsSubcategory = new Subcategory("Flights", travelCategory);
        Subcategory accommodationsSubcategory = new Subcategory("Accommodations", travelCategory);

        // Add subcategories to categories
        foodCategory.addSubcategory(fastFoodSubcategory);
        foodCategory.addSubcategory(restaurantsSubcategory);
        travelCategory.addSubcategory(flightsSubcategory);
        travelCategory.addSubcategory(accommodationsSubcategory);

        // Add categories to category manager
        categoryManager.addCategory(foodCategory);
        categoryManager.addCategory(travelCategory);

        // Add subcategories to subcategory manager
        subcategoryManager.addSubcategory(fastFoodSubcategory);
        subcategoryManager.addSubcategory(restaurantsSubcategory);
        subcategoryManager.addSubcategory(flightsSubcategory);
        subcategoryManager.addSubcategory(accommodationsSubcategory);

        // Create and add expenses
        Expense expense1 = new Expense("Burger", 5.99, LocalDate.now());
        expenseManager.addExpense(expense1);
        expenseManager.addExpenseToSubcategory(expense1, fastFoodSubcategory);
        Expense expense2 = new Expense("Pizza", 8.99, LocalDate.now());
        expenseManager.addExpense(expense2);
        expenseManager.addExpenseToSubcategory(expense2, fastFoodSubcategory);

        Expense expense3 = new Expense("Fine Dining", 100.0, LocalDate.now());
        expenseManager.addExpense(expense3);
        expenseManager.addExpenseToSubcategory(expense3, restaurantsSubcategory);

        Expense expense4 = new Expense("Flight to Paris", 500.0, LocalDate.now());
        expenseManager.addExpense(expense4);
        expenseManager.addExpenseToSubcategory(expense4, flightsSubcategory);

        // Retrieve all expenses from expense manager
        List<Expense> allExpenses = expenseManager.getAllExpenses();

        // Verify the correctness of expenses
        assertEquals(4, allExpenses.size());
        assertTrue(allExpenses.contains(expense1));
        assertTrue(allExpenses.contains(expense2));
        assertTrue(allExpenses.contains(expense3));
        assertTrue(allExpenses.contains(expense4));

        // Retrieve all subcategories from subcategory manager
        List<Subcategory> allSubcategories = subcategoryManager.getAllSubcategories();

        // Verify the correctness of subcategories
        assertEquals(4, allSubcategories.size());
        assertTrue(allSubcategories.contains(fastFoodSubcategory));
        assertTrue(allSubcategories.contains(restaurantsSubcategory));
        assertTrue(allSubcategories.contains(flightsSubcategory));
        assertTrue(allSubcategories.contains(accommodationsSubcategory));

        // Retrieve all categories from category manager
        List<Category> allCategories = categoryManager.getAllCategories();

        // Verify the correctness of categories
        assertEquals(2, allCategories.size());
        assertTrue(allCategories.contains(foodCategory));
        assertTrue(allCategories.contains(travelCategory));

        // Remove a subcategory and verify the associated expenses are also removed
        subcategoryManager.removeSubcategory(fastFoodSubcategory, expenseManager);
        assertFalse(allSubcategories.contains(fastFoodSubcategory));
        assertFalse(allExpenses.contains(expense1));
        assertFalse(allExpenses.contains(expense2));

        // Remove a category and verify the associated subcategories and expenses are also removed
        categoryManager.removeCategory(travelCategory, subcategoryManager, expenseManager);
        assertFalse(allCategories.contains(travelCategory));
        assertFalse(allSubcategories.contains(flightsSubcategory));
        assertFalse(allSubcategories.contains(accommodationsSubcategory));
        assertFalse(allExpenses.contains(expense4));
    }


}
