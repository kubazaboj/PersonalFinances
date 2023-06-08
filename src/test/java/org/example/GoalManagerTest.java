package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GoalManagerTest {

    @Mock
    private ExpenseManager expenseManager;

    private GoalManager goalManager;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        goalManager = new GoalManager();
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

        when(expenseManager.getAllExpenses()).thenReturn(expenses);

        double totalExpensesForCategoryGoal = goalManager.getTotalExpensesForGoal(goalWithCategory, expenseManager);
        double totalExpensesForSubcategoryGoal = goalManager.getTotalExpensesForGoal(goalWithSubcategory, expenseManager);

        assertEquals(2 * expenseAmount, totalExpensesForCategoryGoal, 0.01);
        assertEquals(expenseAmount, totalExpensesForSubcategoryGoal, 0.01);
    }
    // Add more test methods for other functionality as needed
}
