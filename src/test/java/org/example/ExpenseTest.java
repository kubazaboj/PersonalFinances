package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


class ExpenseTest {

    Expense expense;
    Subcategory subcategory;

    @BeforeEach
    void beforeEach(){
        subcategory = Mockito.mock(Subcategory.class);
        expense = new Expense("Test Expense", 100.0, LocalDate.now(), subcategory);
        }

    @Test
    void testGetDescription() {
        String description = "Test Expense";

        String actualDescription = expense.getDescription();

        assertEquals(description, actualDescription);
    }

    @ParameterizedTest
    @CsvSource({
            "-100",
            "0",
            "100"
    })
    void amountLegalValueAndGetCorrectly(double amount) {
        if (amount <= 0) {
            assertThrows(IllegalArgumentException.class, () -> {
                Expense newExpense = new Expense("Test Expense", amount, LocalDate.now(), null);
            });
        } else {
            Expense newExpense = new Expense("Test Expense", amount, LocalDate.now(), null);
            assertEquals(amount, newExpense.getAmount());
        }
    }

    @Test
    void testGetDate() {
        assertEquals(LocalDate.now(), expense.getDate());
    }

    @Test
    void testGetSubcategory() {
        assertEquals(subcategory, expense.getSubcategory());
    }

    @Test
    public void testSetSubcategory() {
        Subcategory newSubcategory = Mockito.mock(Subcategory.class);

        expense.setSubcategory(newSubcategory);

        assertEquals(newSubcategory, expense.getSubcategory());
    }

    @Test
    void testRemoveSubcategory() {

        expense.removeSubcategory();

        assertNull(expense.getSubcategory());
    }
}