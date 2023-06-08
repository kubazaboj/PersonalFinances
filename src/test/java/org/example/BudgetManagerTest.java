package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetManagerTest {

    private BudgetManager budgetManager;

    @BeforeEach
    public void beforeEach() {
        budgetManager = new BudgetManager();
    }

    @Test
    public void testAddBudget() {
        Budget budget = mock(Budget.class);
        budgetManager.addBudget(budget);

        List<Budget> budgets = budgetManager.getAllBudgets();

        assertEquals(1, budgets.size());
        assertEquals(budget, budgets.get(0));
    }

    @Test
    public void testRemoveBudget() {
        Budget budget = mock(Budget.class);
        budgetManager.addBudget(budget);
        assertEquals(1, budgetManager.getAllBudgets().size());

        budgetManager.removeBudget(budget);
        assertEquals(0, budgetManager.getAllBudgets().size());
    }

    @Test
    public void testGetRemainingBudget() {
        Budget budget = mock(Budget.class);
        when(budget.getAllocatedBudget()).thenReturn(1000.0);
        when(budget.getUsedBudget()).thenReturn(500.0);

        double remainingBudget = budgetManager.getRemainingBudget(budget);

        assertEquals(500.0, remainingBudget);
    }

    @Test
    public void testGetBudgetByYearMonth() {
        YearMonth yearMonth = YearMonth.of(2023, 7);
        Budget budget = mock(Budget.class);
        when(budget.getYearMonth()).thenReturn(yearMonth);

        budgetManager.addBudget(budget);

        Budget retrievedBudget = budgetManager.getBudgetByYearMonth(yearMonth);

        assertEquals(budget, retrievedBudget);
    }

    @Test
    public void testIsBudgetOverdraftedForMonth() {
        YearMonth yearMonth = YearMonth.of(2023, 7);
        Budget budget = mock(Budget.class);
        when(budget.getYearMonth()).thenReturn(yearMonth);
        when(budget.getUsedBudget()).thenReturn(1200.0);
        when(budget.getAllocatedBudget()).thenReturn(1000.0);

        budgetManager.addBudget(budget);

        assertTrue(budgetManager.isBudgetOverdraftedForMonth(yearMonth));
    }

    @Test
    public void testGetOverdraftedMonthsForYear() {
        int year = 2023;

        YearMonth overdraftedYearMonth1 = YearMonth.of(year, 7);
        YearMonth overdraftedYearMonth2 = YearMonth.of(year, 9);

        Budget budget1 = mock(Budget.class);
        when(budget1.getYearMonth()).thenReturn(overdraftedYearMonth1);
        when(budget1.getUsedBudget()).thenReturn(1200.0);
        when(budget1.getAllocatedBudget()).thenReturn(1000.0);

        Budget budget2 = mock(Budget.class);
        when(budget2.getYearMonth()).thenReturn(overdraftedYearMonth2);
        when(budget2.getUsedBudget()).thenReturn(1500.0);
        when(budget2.getAllocatedBudget()).thenReturn(1000.0);

        Budget budget3 = mock(Budget.class);
        when(budget3.getYearMonth()).thenReturn(YearMonth.of(year, 8));
        when(budget3.getUsedBudget()).thenReturn(800.0);
        when(budget3.getAllocatedBudget()).thenReturn(1000.0);

        budgetManager.addBudget(budget1);
        budgetManager.addBudget(budget2);
        budgetManager.addBudget(budget3);

        List<YearMonth> overdraftedMonths = budgetManager.getOverdraftedMonthsForYear(year);

        assertEquals(2, overdraftedMonths.size());
        assertTrue(overdraftedMonths.contains(overdraftedYearMonth1));
        assertTrue(overdraftedMonths.contains(overdraftedYearMonth2));
    }
}