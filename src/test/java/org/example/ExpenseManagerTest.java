package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {

    ExpenseManager expenseManager = new ExpenseManager(new Budget(10, YearMonth.now()));

    @Test
    public void testAddExpense() {
        Expense expense = new Expense("Burger", 5.99, LocalDate.now());

        expenseManager.addExpense(expense);

        assertTrue(expenseManager.getAllExpenses().contains(expense));
    }

    @Test
    public void testRemoveExpense() {
        Expense expense = new Expense("Burger", 5.99, LocalDate.now());

        expenseManager.addExpense(expense);
        expenseManager.removeExpense(expense);

        assertFalse(expenseManager.getAllExpenses().contains(expense));
    }

    // Add more test methods for other functionality of the ExpenseManager class

}
