package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.List;

public class BudgetManagerTest {

    BudgetManager budgetManager = new BudgetManager();
    YearMonth month1 = YearMonth.of(2023, 6);
    Budget budget = new Budget(100.0, month1);



    @Test
    public void testAddBudget() {

        budgetManager.addBudget(budget);
        List<Budget> allBudgets = budgetManager.getAllBudgets();

        Assertions.assertEquals(1, allBudgets.size());
        Assertions.assertTrue(allBudgets.contains(budget));
    }

    @Test
    public void testRemoveBudget() {

        budgetManager.addBudget(budget);
        budgetManager.removeBudget(budget);
        List<Budget> allBudgets = budgetManager.getAllBudgets();

        Assertions.assertEquals(0, allBudgets.size());
        Assertions.assertFalse(allBudgets.contains(budget));
    }

    @Test
    public void testGetBudgetByYearMonth() {


        YearMonth month2 = YearMonth.of(2022, 6);
        BudgetManager budgetManager = new BudgetManager();
        Budget budget2 = new Budget(200.0, month2);

        budgetManager.addBudget(budget);
        budgetManager.addBudget(budget2);

        Budget foundBudget = budgetManager.getBudgetByYearMonth(month1);
        Assertions.assertEquals(budget, foundBudget);

        foundBudget = budgetManager.getBudgetByYearMonth(month2);
        Assertions.assertEquals(budget2, foundBudget);

        foundBudget = budgetManager.getBudgetByYearMonth(YearMonth.of(2022, 7));
        Assertions.assertNull(foundBudget);
    }
}
