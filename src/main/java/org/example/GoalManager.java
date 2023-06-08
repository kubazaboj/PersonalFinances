package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalManager {
    private List<Goal> goals;

    public GoalManager() {
        this.goals = new ArrayList<Goal>();
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public void removeGoal(Goal goal) {
        goals.remove(goal);
    }

    public List<Goal> getAllGoals() {
        return goals;
    }
    public double getTotalExpensesForGoal(Goal goal, ExpenseManager expenseManager) {
        double totalExpenses = 0.0;
        List<Expense> expenses = expenseManager.getAllExpenses();

        Category goalCategory = goal.getGoalCategory();
        Subcategory goalSubcategory = goal.getGoalSubcategory();
        LocalDate goalStartDate = goal.getTimeFrameStart();
        LocalDate goalEndDate = goal.getTimeFrameEnd();

        for (Expense expense : expenses) {
            Subcategory expenseSubcategory = expense.getSubcategory();
            if ((goalCategory != null && expenseSubcategory != null && expenseSubcategory.getCategory().equals(goalCategory)) ||
                    (goalSubcategory != null && expenseSubcategory != null && expenseSubcategory.equals(goalSubcategory))) {
                LocalDate expenseDate = expense.getDate();
                if (isDateInRange(expenseDate, goalStartDate, goalEndDate)) {
                    totalExpenses += expense.getAmount();
                }
            }
        }

        return totalExpenses;
    }

    private boolean isDateInRange(LocalDate date, LocalDate rangeStart, LocalDate rangeEnd) {
        return !date.isBefore(rangeStart) && !date.isAfter(rangeEnd);
    }

}
