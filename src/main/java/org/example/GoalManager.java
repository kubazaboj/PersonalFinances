package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public boolean wasGoalMet(Goal goal, ExpenseManager expenseManager){
        return goal.getTargetAmount() >= getTotalExpensesForGoal(goal, expenseManager);
    }

    public double getTotalExpensesForGoal(Goal goal, ExpenseManager expenseManager) {
        double totalExpenses = 0.0;
        List<Expense> expenses = expenseManager.getAllExpenses();

        Category goalCategory = goal.getGoalCategory();
        Subcategory goalSubcategory = goal.getGoalSubcategory();
        LocalDate goalStartDate = goal.getTimeFrameStart();
        LocalDate goalEndDate = goal.getTimeFrameEnd();
        if (goalSubcategory != null) {
            for (Expense expense : expenses) {
                Subcategory expenseSubcategory = expense.getSubcategory();
                if (Objects.equals(expenseSubcategory.getName(), goalSubcategory.getName())) {
                    LocalDate expenseDate = expense.getDate();
                    if (isDateInRange(expenseDate, goalStartDate, goalEndDate)) {
                        totalExpenses += expense.getAmount();
                    }
                }
            }
        } else if (goalCategory != null) {
            for (Expense expense : expenses) {
                Subcategory expenseSubcategory = expense.getSubcategory();
                Category expenseCategory = expenseSubcategory.getCategory();
                if (Objects.equals(expenseCategory.getName(), goalCategory.getName())) {
                    LocalDate expenseDate = expense.getDate();
                    if (isDateInRange(expenseDate, goalStartDate, goalEndDate)) {
                        totalExpenses += expense.getAmount();
                    }
                }
            }
        }
        return totalExpenses;
    }

    private boolean isDateInRange(LocalDate date, LocalDate rangeStart, LocalDate rangeEnd) {
        return !date.isBefore(rangeStart) && !date.isAfter(rangeEnd);
    }

}
