package org.example;

import java.time.LocalDate;
import java.util.List;

public class GoalManager {
    private List<Goal> goals;

    public GoalManager(List<Goal> goals) {
        this.goals = goals;
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

    public double getTotalExpensesForGoal(Goal goal, List<Expense> expenses) {
        double totalExpenses = 0.0;
        Category category = goal.getGoalCategory();
        Subcategory subcategory = goal.getGoalSubcategory();

        // Iterate over the expenses and filter based on category/subcategory and time range
        for (Expense expense : expenses) {
            // Check if the expense matches the category or subcategory
            Subcategory expenseSubcategory = expense.getSubcategory();
            if (expenseSubcategory.getCategory().equals(category) || expenseSubcategory.equals(subcategory)) {
                // Check if the expense date is within the specified time range
                LocalDate expenseDate = expense.getDate();
                if (isDateInRange(expenseDate, goal.getTimeFrameStart(), goal.getTimeFrameEnd())) {
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
