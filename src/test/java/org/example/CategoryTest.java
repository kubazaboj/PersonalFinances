package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testGetName() {
        Category category = new Category("Food");
        assertEquals("Food", category.getName());
    }

    @Test
    public void testAddSubcategory() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        category.addSubcategory(subcategory);

        assertTrue(category.getSubcategories().contains(subcategory));
    }

    @Test
    public void testRemoveSubcategory() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        category.addSubcategory(subcategory);
        category.removeSubcategory(subcategory);

        assertFalse(category.getSubcategories().contains(subcategory));
    }

    // Add more test methods for other functionality of the Category class

}