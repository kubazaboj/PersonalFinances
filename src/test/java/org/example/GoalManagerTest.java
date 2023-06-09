package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GoalManagerTest {

    @Mock
    private ExpenseManager expenseManager;

    private GoalManager goalManager;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        goalManager = new GoalManager();
        expenseManager = new ExpenseManager(new Budget(YearMonth.now()));
    }

    @Test
    public void testAddGoal() {
        Goal goal = new Goal.Builder("Goal", 100.0, GoalType.MONTHLY).build();
        goalManager.addGoal(goal);
        List<Goal> goals = goalManager.getAllGoals();
        assertEquals(1, goals.size());
        assertEquals(goal, goals.get(0));
    }

    @Test
    public void testRemoveGoal() {
        Goal goal = new Goal.Builder("Goal", 100.0, GoalType.MONTHLY).build();
        goalManager.addGoal(goal);
        goalManager.removeGoal(goal);
        List<Goal> goals = goalManager.getAllGoals();
        assertEquals(0, goals.size());
    }

    @Test
    public void testGetAllGoals() {
        Goal goal1 = new Goal.Builder("Goal 1", 100.0, GoalType.MONTHLY).build();
        Goal goal2 = new Goal.Builder("Goal 2", 200.0, GoalType.WEEKLY).build();
        goalManager.addGoal(goal1);
        goalManager.addGoal(goal2);
        List<Goal> goals = goalManager.getAllGoals();
        assertEquals(2, goals.size());
        assertEquals(goal1, goals.get(0));
        assertEquals(goal2, goals.get(1));
    }

    @Test
    public void testGetTotalExpensesForGoal() {
        Category category = new Category("Food");
        Subcategory subcategoryGroceries = new Subcategory("Groceries", category);
        Subcategory subcategoryFastFood = new Subcategory("FastFood", category);
        double expenseAmount = 50.0;
        LocalDate expenseDate = LocalDate.now();

        Goal goalWithCategory = new Goal.Builder("Goal with Category", 100.0, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .category(category)
                .build();

        Goal goalWithSubcategory = new Goal.Builder("Goal with Subcategory", 200.0, GoalType.WEEKLY)
                .targetWeek(new Week(LocalDate.now()))
                .subcategory(subcategoryGroceries)
                .build();

        Expense expense1 = new Expense("Kaufland", expenseAmount, expenseDate, subcategoryGroceries);
        Expense expense2 = new Expense("McDonalds", expenseAmount, expenseDate, subcategoryFastFood);

        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense1);
        expenses.add(expense2);

        ExpenseManager mockExpenseManager = Mockito.mock(ExpenseManager.class);

        when(mockExpenseManager.getAllExpenses()).thenReturn(expenses);

        double totalExpensesForCategoryGoal = goalManager.getTotalExpensesForGoal(goalWithCategory, mockExpenseManager);
        double totalExpensesForSubcategoryGoal = goalManager.getTotalExpensesForGoal(goalWithSubcategory, mockExpenseManager);

        assertEquals(2 * expenseAmount, totalExpensesForCategoryGoal, 0.01);
        assertEquals(expenseAmount, totalExpensesForSubcategoryGoal, 0.01);
    }

    //Equivalence test for meeting the goal method
    @Test
    public void testWasGoalMetExpensesLess() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Groceries", category);

        Goal categoryGoal = new Goal.Builder("Food saving", 300, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .category(category)
                .build();

        Goal subcategoryGoal = new Goal.Builder("Food saving", 300, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .subcategory(subcategory)
                .build();

        expenseManager.addExpense(new Expense("Kaufland", 200, LocalDate.now(), subcategory));
        assertTrue(goalManager.wasGoalMet(categoryGoal, expenseManager));
        assertTrue(goalManager.wasGoalMet(subcategoryGoal, expenseManager));
    }

    @Test
    public void testWasGoalMetExpensesEqual() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Groceries", category);

        Goal categoryGoal = new Goal.Builder("Food saving", 300, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .category(category)
                .build();

        Goal subcategoryGoal = new Goal.Builder("Food saving", 300, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .subcategory(subcategory)
                .build();
        ExpenseManager expenseManager = new ExpenseManager(new Budget(YearMonth.now()));
        expenseManager.addExpense(new Expense("Kaufland", 300, LocalDate.now(), subcategory));
        assertTrue(goalManager.wasGoalMet(categoryGoal, expenseManager));
        assertTrue(goalManager.wasGoalMet(subcategoryGoal, expenseManager));
    }

    @Test
    public void testWasGoalMetExpensesMore() {
        Category category = new Category("Food");
        Subcategory subcategory = new Subcategory("Groceries", category);

        Goal categoryGoal = new Goal.Builder("Food saving", 300, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .category(category)
                .build();

        Goal subcategoryGoal = new Goal.Builder("Food saving", 300, GoalType.MONTHLY)
                .targetMonth(YearMonth.of(2023,6))
                .subcategory(subcategory)
                .build();
        ExpenseManager expenseManager = new ExpenseManager(new Budget(YearMonth.now()));
        expenseManager.addExpense(new Expense("Kaufland", 400, LocalDate.now(), subcategory));
        assertFalse(goalManager.wasGoalMet(categoryGoal, expenseManager));
        assertFalse(goalManager.wasGoalMet(subcategoryGoal, expenseManager));
    }

    @ParameterizedTest
    @CsvSource({
            "1000.0, 500.0, 500.0", // Positive value
            "1000.0, 1500.0, -500.0", // Negative value
            "1000.0, 1000.0, 0.0" // Zero value
    })
    public void testGoalSavingsAmount(double targetAmount, double totalExpenses, double expectedSavings) {
        Category holidayCategory = new Category("Holidays");
        Goal savingsGoal = new Goal.Builder("Vacation saving", targetAmount, GoalType.MONTHLY)
                .targetMonth(YearMonth.now())
                .category(holidayCategory)
                .build();

       Expense vacationExpense = new Expense("Vacation", totalExpenses, LocalDate.now(), new Subcategory("Holidays", holidayCategory));
        expenseManager.addExpense(vacationExpense);
        // Calculate the savings amount
        double savingsAmount = goalManager.goalMoneySavedAmount(savingsGoal, expenseManager);

        // Assert the expected savings amount
        assertEquals(expectedSavings, savingsAmount);
    }

}
