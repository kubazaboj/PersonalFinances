package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    static Category category;
    Subcategory subcategory;

    @BeforeAll
    public static void beforeAll(){
        category = new Category("Test Category");
    }

    @BeforeEach
    public void beforeEach(){
        subcategory = Mockito.mock(Subcategory.class);

    }

    @Test
    void testAddSubcategory() {
        category.addSubcategory(subcategory);

        assertTrue(category.getSubcategories().contains(subcategory));
    }

    @Test
    void testRemoveSubcategory() {
        category.addSubcategory(subcategory);
        category.removeSubcategory(subcategory);

        assertFalse(category.getSubcategories().contains(subcategory));
    }
}