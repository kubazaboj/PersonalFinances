package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SubcategoryTest {

    @Test
    public void testGetName() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        assertEquals("Fast Food", subcategory.getName());
    }

    @Test
    public void testGetCategory() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        assertEquals(category, subcategory.getCategory());
    }

    @Test
    public void testAddExpense() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);
        Expense expense = new Expense("Burger", 5.99, LocalDate.now());

        subcategory.addExpense(expense);

        assertTrue(subcategory.getExpenses().contains(expense));
    }

    @Test
    public void testRemoveExpense() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);
        Expense expense = new Expense("Burger", 5.99, LocalDate.now());

        subcategory.addExpense(expense);
        subcategory.removeExpense(expense);

        assertFalse(subcategory.getExpenses().contains(expense));
    }

    // Add more test methods for other functionality of the Subcategory class

}