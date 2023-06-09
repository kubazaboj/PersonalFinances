package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class IncomeTest {

    Income income;
    Subcategory subcategory;

    @BeforeEach
    void beforeEach(){
        subcategory = Mockito.mock(Subcategory.class);
        income = new Income("Test Income", 100.0, LocalDate.now(), subcategory);
    }


    @ParameterizedTest
    @CsvSource({
            "100.0",
            "-100.0",
            "0.0"
    })
    public void testGetAmountOrException(double amount) {
        if (amount <= 0) {
            assertThrows(IllegalArgumentException.class, () -> {
                Income newIncome = new Income("Test Income", amount, LocalDate.now(), null);
            });
        } else {
            Income newIncome = new Income("Test Income", amount, LocalDate.now(), null);
            assertEquals(amount, newIncome.getAmount());
        }
    }

    @Test
    public void testRemoveSubcategory() {

        income.removeSubcategory();

        assertNull(income.getSubcategory());
    }

    @Test
    void testGetDate() {
        assertEquals(LocalDate.now(), income.getDate());
    }

    @Test
    void testGetSubcategory() {
        assertEquals(subcategory, income.getSubcategory());
    }

    @Test
    public void testSetSubcategory() {
        Subcategory newSubcategory = Mockito.mock(Subcategory.class);

        income.setSubcategory(newSubcategory);

        assertEquals(newSubcategory, income.getSubcategory());
    }

}
