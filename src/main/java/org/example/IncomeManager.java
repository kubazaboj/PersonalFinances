package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeManager {
    private List<Income> incomes;
    private Budget budget;

    public IncomeManager(Budget budget) {
        incomes = new ArrayList<>();
        this.budget = budget;
    }

    public void addIncomeToSubcategory(Income income, Subcategory subcategory) {
        subcategory.addIncome(income);
        income.setSubcategory(subcategory);
    }

    public void addIncome(Income income) {
        incomes.add(income);
        budget.increaseActualBudget(income.getAmount());
    }

    public void removeIncome(Income income) {
        incomes.remove(income);
        budget.decreaseActualBudget(income.getAmount());
    }

    public List<Income> getAllIncomes() {
        return incomes;
    }

    public double getTotalIncomeForDay(LocalDate date) {
        double totalIncome = 0;
        for (Income income : incomes) {
            if (income.getDate().equals(date)) {
                totalIncome += income.getAmount();
            }
        }
        return totalIncome;
    }

    public double getTotalIncomeForWeek(LocalDate startOfWeek, LocalDate endOfWeek) {
        double totalIncome = 0;
        for (Income income : incomes) {
            LocalDate incomeDate = income.getDate();
            if (incomeDate.isAfter(startOfWeek) || incomeDate.isEqual(startOfWeek)) {
                if (incomeDate.isBefore(endOfWeek) || incomeDate.isEqual(endOfWeek)) {
                    totalIncome += income.getAmount();
                }
            }
        }
        return totalIncome;
    }

    public double getTotalIncomeForMonth(LocalDate month) {
        double totalIncome = 0;
        for (Income income : incomes) {
            if (income.getDate().getMonth().equals(month.getMonth())) {
                totalIncome += income.getAmount();
            }
        }
        return totalIncome;
    }

    public double getTotalIncomeForYear(int year) {
        double totalIncome = 0;
        for (Income income : incomes) {
            if (income.getDate().getYear() == year) {
                totalIncome += income.getAmount();
            }
        }
        return totalIncome;
    }
}