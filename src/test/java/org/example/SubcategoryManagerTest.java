package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubcategoryManagerTest {

    @Test
    public void testAddSubcategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        subcategoryManager.addSubcategory(subcategory);

        assertTrue(subcategoryManager.getAllSubcategories().contains(subcategory));
    }

    @Test
    public void testRemoveSubcategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        subcategoryManager.addSubcategory(subcategory);
        subcategoryManager.removeSubcategory(subcategory, new ExpenseManager(new Budget(10, YearMonth.now())));

        assertFalse(subcategoryManager.getAllSubcategories().contains(subcategory));
    }

    @Test
    public void testGetCategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        subcategoryManager.addSubcategory(subcategory);

        assertEquals(category, subcategoryManager.getCategory(subcategory));
    }

    @Test
    public void testGetAllExpenses() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);
        Expense expense1 = new Expense("Burger", 5.99, LocalDate.now());
        Expense expense2 = new Expense("Pizza", 8.99, LocalDate.now());

        subcategory.addExpense(expense1);
        subcategory.addExpense(expense2);

        List<Expense> allExpenses = subcategoryManager.getAllSubcategoryExpenses(subcategory);

        assertTrue(allExpenses.contains(expense1));
        assertTrue(allExpenses.contains(expense2));
    }

    // Add more test methods for other functionality of the SubcategoryManager class

}
