package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SubcategoryManagerTest {

    @Mock
    private ExpenseManager expenseManager;

    public SubcategoryManagerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddSubcategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = mock(Category.class);
        Subcategory subcategory = mock(Subcategory.class);

        when(subcategory.getCategory()).thenReturn(category);

        subcategoryManager.addSubcategory(subcategory);

        assertEquals(1, subcategoryManager.getSubcategories().size());
        verify(category).addSubcategory(subcategory);
    }

    @Test
    public void testRemoveSubcategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = mock(Category.class);
        Subcategory subcategory = mock(Subcategory.class);
        Expense expense = mock(Expense.class);

        when(subcategory.getCategory()).thenReturn(category);
        when(subcategory.getExpenses()).thenReturn(List.of(expense));

        subcategoryManager.addSubcategory(subcategory);

        subcategoryManager.removeSubcategory(subcategory, expenseManager);

        assertEquals(0, subcategoryManager.getSubcategories().size());
        verify(category).removeSubcategory(subcategory);
        verify(expenseManager).removeExpense(expense);
    }

    @Test
    public void testGetCategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        Category category = mock(Category.class);
        Subcategory subcategory = mock(Subcategory.class);

        when(subcategory.getCategory()).thenReturn(category);

        subcategoryManager.addSubcategory(subcategory);

        Category result = subcategoryManager.getCategory(subcategory);

        assertEquals(category, result);
    }
    // Add more tests as needed
}