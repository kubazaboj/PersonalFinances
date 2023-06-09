package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {

    private Budget budget;

    @BeforeEach
    public void beforeEach() {
        budget = new Budget(YearMonth.now());
    }

    @Test
    public void testGetUsedBudget() {
        double expectedUsedBudget = 100.0;
        budget.increaseUsedBudget(expectedUsedBudget);
        assertEquals(expectedUsedBudget, budget.getUsedBudget());
    }

    @Test
    public void testGetAllocatedBudget() {
        double expectedAllocatedBudget = 500.0;
        budget.setAllocatedBudget(expectedAllocatedBudget);
        assertEquals(expectedAllocatedBudget, budget.getAllocatedBudget());
    }

    @Test
    public void testIncreaseUsedBudget() {
        double initialUsedBudget = budget.getUsedBudget();
        double amount = 50.0;
        budget.increaseUsedBudget(amount);
        assertEquals(initialUsedBudget + amount, budget.getUsedBudget());
    }

    @Test
    public void testDecreaseAllocatedBudget() {
        double initialAllocatedBudget = budget.getAllocatedBudget();
        double amount = 100.0;
        budget.decreaseAllocatedBudget(amount);
        assertEquals(initialAllocatedBudget - amount, budget.getAllocatedBudget());
    }

    @Test
    public void testIncreaseAllocatedBudget() {
        double initialAllocatedBudget = budget.getAllocatedBudget();
        double amount = 200.0;
        budget.increaseAllocatedBudget(amount);
        assertEquals(initialAllocatedBudget + amount, budget.getAllocatedBudget());
    }

    @Test
    public void testDecreaseUsedBudget() {
        double initialUsedBudget = budget.getUsedBudget();
        double amount = 80.0;
        budget.decreaseUsedBudget(amount);
        assertEquals(initialUsedBudget - amount, budget.getUsedBudget());
    }

    @Test
    public void testGetYearMonth() {
        YearMonth expectedYearMonth = YearMonth.of(2023, 6);
        budget.setYearMonth(expectedYearMonth);
        assertEquals(expectedYearMonth, budget.getYearMonth());
    }

    @Test
    public void testSetYearMonth() {
        YearMonth expectedYearMonth = YearMonth.of(2023, 7);
        budget.setYearMonth(expectedYearMonth);
        assertEquals(expectedYearMonth, budget.getYearMonth());
    }

    @Test
    public void testGetDaysLeftInBudgetCurrentYearMonthIsAfter() {
        YearMonth yearMonth = YearMonth.of(2022, 5);
        budget.setYearMonth(yearMonth);

        long daysLeft = budget.getDaysLeftInBudget();

        assertEquals(0, daysLeft);
    }

    @Test
    public void testGetDaysLeftInBudgetCurrentYearMonthIsBeforeMoreThanMonth() {
        YearMonth yearMonth = YearMonth.now().plusMonths(1);
        budget.setYearMonth(yearMonth);
        long expectedDaysLeft = ChronoUnit.DAYS.between(LocalDate.now(), YearMonth.now().plusMonths(1).atEndOfMonth());

        assertEquals(budget.getDaysLeftInBudget(), expectedDaysLeft);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0",
            "100, 0, 0",
            "0, 100, 0",
            "200, 500, 40",
            "400, 200, 200",
            "200, 200, 100"
    })
    public void testGetSpendingPercentage(double usedBudget, double allocatedBudget, double expectedPercentage) {
        budget.setAllocatedBudget(allocatedBudget);
        budget.increaseUsedBudget(usedBudget);

        double actualPercentage = budget.getSpendingPercentage();

        assertEquals(expectedPercentage, actualPercentage, 0.001);
    }

    @Test
    public void testIsBudgetOverdraftedUsedBudgetLessThanAllocatedReturnsFalse() {
        budget.setAllocatedBudget(500);
        budget.increaseUsedBudget(300);

        assertFalse(budget.isBudgetOverdrafted());
    }

    @Test
    public void testIsBudgetOverdraftedUsedBudgetGreaterThanAllocatedReturnsTrue() {
        budget.setAllocatedBudget(500);
        budget.increaseUsedBudget(600);

        assertTrue(budget.isBudgetOverdrafted());
    }
}