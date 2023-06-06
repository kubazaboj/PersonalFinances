package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryManagerTest {
    static Category category;
    CategoryManager categoryManager = new CategoryManager();
    @BeforeAll
    public static void beforeAll(){
        category = new Category("Food");

    }

    @Test
    public void testAddCategory() {

        categoryManager.addCategory(category);

        assertTrue(categoryManager.getAllCategories().contains(category));
    }

    @Test
    public void testRemoveCategory() {
        SubcategoryManager subcategoryManager = new SubcategoryManager();
        ExpenseManager expenseManager = new ExpenseManager(new Budget(10, YearMonth.now()));

        categoryManager.addCategory(category);
        categoryManager.removeCategory(category, subcategoryManager, expenseManager);

        assertFalse(categoryManager.getAllCategories().contains(category));
    }

    @Test
    public void testGetAllSubcategories() {
        Subcategory subcategory1 = new Subcategory("Fast Food", category);
        Subcategory subcategory2 = new Subcategory("Healthy Food", category);

        category.addSubcategory(subcategory1);
        category.addSubcategory(subcategory2);

        List<Subcategory> allSubcategories = categoryManager.getAllCategorySubcategories(category);

        assertTrue(allSubcategories.contains(subcategory1));
        assertTrue(allSubcategories.contains(subcategory2));
    }

    // Add more test methods for other functionality of the CategoryManager class

}