package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    static Expense expense;


    @BeforeAll
    public static void beforeAll(){
        expense = new Expense("Burger", 5.99, LocalDate.now());
    }

    @Test
    public void testGetDescription() {

        assertEquals("Burger", expense.getDescription());
    }

    @Test
    public void testGetAmount() {

        assertEquals(5.99, expense.getAmount(), 0.0);
    }

    @Test
    public void testGetDate() {
        LocalDate date = LocalDate.now();

        assertEquals(date, expense.getDate());
    }

    @Test
    public void testAddSubcategory() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        expense.setSubcategory(subcategory);

        assertNotNull(expense.getSubcategory());
    }

    @Test
    public void testRemoveSubcategory() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Fast Food", category);

        expense.setSubcategory(subcategory);
        expense.removeSubcategory();

        assertNull(expense.getSubcategory());
    }

    /*@Test
    public void testExpenseDescriptionIsString() {
        // Arrange
        double amount = 5.99;
        LocalDate date = LocalDate.now();
        String invalidDescription = "12345";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Expense(invalidDescription, amount, date);
        });

        // Assert
        String expectedErrorMessage = "Description must be a string";
        String actualErrorMessage = exception.getMessage();
        assertTrue(actualErrorMessage.contains(expectedErrorMessage));
    }*/

    // Add more test methods for other functionality of the Expense class

}
