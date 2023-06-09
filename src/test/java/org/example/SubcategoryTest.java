package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SubcategoryTest {
    Category category;
    Subcategory subcategory;
    Expense expense;
    Income income;

    @BeforeEach
    void beforeEach() {
        category = mock(Category.class);
        expense = mock(Expense.class);
        income = mock(Income.class);
        subcategory = new Subcategory("Test Subcategory", category);
        subcategory.addExpense(expense);
        subcategory.addIncome(income);
    }

    @Test
    public void testAddExpense() {
        assertTrue(subcategory.getExpenses().contains(expense));
    }

    @Test
    public void testRemoveExpense() {
        subcategory.removeExpense(expense);

        assertFalse(subcategory.getExpenses().contains(expense));
    }

    @Test
    public void testAddIncome() {

        subcategory.addIncome(income);

        assertTrue(subcategory.getIncomes().contains(income));
    }

    @Test
    public void testRemoveIncome() {

        subcategory.removeIncome(income);

        assertFalse(subcategory.getIncomes().contains(income));
    }

    @Test
    public void testGetName() {

        assertEquals("Test Subcategory", subcategory.getName());
    }

    @Test
    public void testGetCategory() {

        assertEquals(category, subcategory.getCategory());
    }

    @Test
    public void testSetCategory() {
        Category category2 = mock(Category.class);

        subcategory.setCategory(category2);

        assertEquals(category2, subcategory.getCategory());
    }

    @Test
    public void testRemoveCategory() {
        subcategory.removeCategory();

        assertNull(subcategory.getCategory());
    }
}
