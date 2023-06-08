package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryManagerTest {

    private CategoryManager categoryManager;
    private SubcategoryManager subcategoryManager;
    private ExpenseManager expenseManager;

    @BeforeEach
    public void beforeEach() {
        categoryManager = new CategoryManager();
        subcategoryManager = mock(SubcategoryManager.class);
        expenseManager = mock(ExpenseManager.class);
    }

    @Test
    public void testAddCategory() {
        Category category = mock(Category.class);
        categoryManager.addCategory(category);

        List<Category> categories = categoryManager.getAllCategories();

        assertEquals(1, categories.size());
        assertEquals(category, categories.get(0));
    }

    @Test
    public void testRemoveCategory() {
        Category category = mock(Category.class);
        Subcategory subcategory1 = mock(Subcategory.class);
        Subcategory subcategory2 = mock(Subcategory.class);
        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(subcategory1);
        subcategories.add(subcategory2);

        when(category.getSubcategories()).thenReturn(subcategories);

        categoryManager.addCategory(category);
        assertEquals(1, categoryManager.getAllCategories().size());

        categoryManager.removeCategory(category, subcategoryManager, expenseManager);

        assertEquals(0, categoryManager.getAllCategories().size());
        verify(subcategoryManager).removeSubcategory(subcategory1, expenseManager);
        verify(subcategoryManager).removeSubcategory(subcategory2, expenseManager);
    }

    @Test
    public void testGetAllCategorySubcategories() {
        Category category = mock(Category.class);
        Subcategory subcategory1 = mock(Subcategory.class);
        Subcategory subcategory2 = mock(Subcategory.class);
        List<Subcategory> subcategories = new ArrayList<>();
        subcategories.add(subcategory1);
        subcategories.add(subcategory2);

        when(category.getSubcategories()).thenReturn(subcategories);

        List<Subcategory> allSubcategories = categoryManager.getAllCategorySubcategories(category);

        assertEquals(2, allSubcategories.size());
        assertTrue(allSubcategories.contains(subcategory1));
        assertTrue(allSubcategories.contains(subcategory2));
    }
}