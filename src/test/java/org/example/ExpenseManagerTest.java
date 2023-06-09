package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExpenseManagerTest {

    private ExpenseManager expenseManager;
    private Budget budget;

    @BeforeEach
    public void setUp() {
        budget = mock(Budget.class);
        expenseManager = new ExpenseManager(budget);
    }

    @Test
    public void testAddExpense() {
        Expense expense = mock(Expense.class);
        Subcategory subcategory = mock(Subcategory.class);
        double amount = 50.0;

        when(expense.getSubcategory()).thenReturn(subcategory);
        when(expense.getAmount()).thenReturn(amount);

        expenseManager.addExpense(expense);

        verify(subcategory).addExpense(expense);
        verify(budget).increaseUsedBudget(amount);
    }

    @Test
    public void testRemoveExpense() {
        Expense expense = mock(Expense.class);
        double amount = 50.0;

        when(expense.getAmount()).thenReturn(amount);

        expenseManager.removeExpense(expense);

        verify(budget).decreaseUsedBudget(amount);
    }

    @Test
    public void testGetTotalExpenses() {
        Expense expense1 = mock(Expense.class);
        Expense expense2 = mock(Expense.class);
        double amount1 = 50.0;
        double amount2 = 75.0;

        when(expense1.getAmount()).thenReturn(amount1);
        when(expense2.getAmount()).thenReturn(amount2);

        expenseManager.addExpense(expense1);
        expenseManager.addExpense(expense2);

        double totalExpenses = expenseManager.getTotalExpenses();

        assertEquals(amount1 + amount2, totalExpenses);
    }

    @Test
    public void testGetTotalExpensesForDay() {
        Expense expense1 = mock(Expense.class);
        Expense expense2 = mock(Expense.class);
        double amount1 = 50.0;
        double amount2 = 75.0;
        LocalDate date = LocalDate.now();

        when(expense1.getAmount()).thenReturn(amount1);
        when(expense2.getAmount()).thenReturn(amount2);
        when(expense1.getDate()).thenReturn(date);
        when(expense2.getDate()).thenReturn(date.minusDays(1));

        expenseManager.addExpense(expense1);
        expenseManager.addExpense(expense2);

        double totalExpenses = expenseManager.getTotalExpensesForDay(date);

        assertEquals(amount1, totalExpenses);
    }

    // Add more test methods for other functionality as needed
}
