package org.example;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoalTest {

    Category category;

    @BeforeEach
    public void beforeEach(){
        category = new Category("Category");
    }

    @Test
    public void testGoalWithCategoryAndDailyType() {
        LocalDate targetDay = LocalDate.now().plusDays(5);
        Goal goal = new Goal.Builder("Goal", 100.0, GoalType.DAILY)
                .category(category)
                .targetDay(targetDay)
                .build();

        assertEquals(category, goal.getGoalCategory());
        assertEquals(targetDay, goal.getGoalDay());
        assertEquals(targetDay, goal.getTimeFrameStart());
        assertEquals(targetDay, goal.getTimeFrameEnd());
        assertEquals(goal.getTimeFrameStart(), goal.getTimeFrameEnd());
        assertEquals(goal.getGoalType(), GoalType.DAILY);
        assertEquals(goal.getTargetAmount(), 100.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithCategoryAndWeeklyType() {
        Week targetWeek = new Week(LocalDate.now());

        Goal goal = new Goal.Builder("Goal", 200.0, GoalType.WEEKLY)
                .category(category)
                .targetWeek(targetWeek)
                .build();

        assertEquals(category, goal.getGoalCategory());
        assertEquals(targetWeek.getStartDate(), goal.getTimeFrameStart());
        assertEquals(targetWeek.getEndDate(), goal.getTimeFrameEnd());
        assertEquals(targetWeek.getEndDate(), LocalDate.now().plusDays(6));
        assertEquals(goal.getGoalType(), GoalType.WEEKLY);
        assertEquals(goal.getTargetAmount(), 200.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithCategoryAndMonthlyType() {
        YearMonth targetMonth = YearMonth.now();
        Goal goal = new Goal.Builder("Goal", 300.0, GoalType.MONTHLY)
                .category(category)
                .targetMonth(targetMonth)
                .build();

        assertEquals(category, goal.getGoalCategory());
        assertEquals(LocalDate.of(YearMonth.now().getYear(), YearMonth.now().getMonthValue(), 1), goal.getTimeFrameStart());
        assertEquals(targetMonth.atEndOfMonth(), goal.getTimeFrameEnd());
        assertEquals(goal.getGoalType(), GoalType.MONTHLY);
        assertEquals(goal.getTargetAmount(), 300.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithCategoryAndYearlyType() {
        Year targetYear = Year.of(LocalDate.now().getYear());
        Goal goal = new Goal.Builder("Goal", 400.0, GoalType.YEARLY)
                .category(category)
                .targetYear(targetYear)
                .build();

        assertEquals(category, goal.getGoalCategory());
        assertEquals(LocalDate.of(targetYear.getValue(), 1, 1), goal.getTimeFrameStart());
        assertEquals(LocalDate.of(targetYear.getValue(), 12, 31), goal.getTimeFrameEnd());
        assertEquals(goal.getYearGoalDaysRemaining(), ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(targetYear.getValue(), 12, 31)));
        assertEquals(goal.getGoalType(), GoalType.YEARLY);
        assertEquals(goal.getTargetAmount(), 400.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithSubcategoryAndDailyType() {
        Subcategory subcategory = new Subcategory("Subcategory", category);
        LocalDate targetDay = LocalDate.now().plusDays(5);

        Goal goal = new Goal.Builder("Goal", 500.0, GoalType.DAILY)
                .subcategory(subcategory)
                .targetDay(targetDay)
                .build();

        assertEquals(subcategory, goal.getGoalSubcategory());
        assertEquals(targetDay, goal.getGoalDay());
        assertEquals(targetDay, goal.getTimeFrameStart());
        assertEquals(targetDay, goal.getTimeFrameEnd());
        assertEquals(goal.getTimeFrameStart(), goal.getTimeFrameEnd());
        assertEquals(goal.getGoalType(), GoalType.DAILY);
        assertEquals(goal.getTargetAmount(), 500.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithSubcategoryAndWeeklyType() {
        Subcategory subcategory = new Subcategory("Subcategory", category);
        Week targetWeek = new Week(LocalDate.now());

        Goal goal = new Goal.Builder("Goal", 600.0, GoalType.WEEKLY)
                .subcategory(subcategory)
                .targetWeek(targetWeek)
                .build();

        assertEquals(subcategory, goal.getGoalSubcategory());
        assertEquals(targetWeek.getStartDate(), goal.getTimeFrameStart());
        assertEquals(targetWeek.getEndDate(), goal.getTimeFrameEnd());
        assertEquals(goal.getGoalType(), GoalType.WEEKLY);
        assertEquals(goal.getTargetAmount(), 600.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithSubcategoryAndMonthlyType() {
        Subcategory subcategory = new Subcategory("Subcategory", category);
        YearMonth targetMonth = YearMonth.now();

        Goal goal = new Goal.Builder("Goal", 700.0, GoalType.MONTHLY)
                .subcategory(subcategory)
                .targetMonth(targetMonth)
                .build();

        assertEquals(subcategory, goal.getGoalSubcategory());
        assertEquals(LocalDate.of(YearMonth.now().getYear(), YearMonth.now().getMonthValue(), 1), goal.getTimeFrameStart());
        assertEquals(targetMonth.atEndOfMonth(), goal.getTimeFrameEnd());
        assertEquals(goal.getGoalType(), GoalType.MONTHLY);
        assertEquals(goal.getTargetAmount(), 700.0);
        assertEquals(goal.getGoalName(), "Goal");
    }

    @Test
    public void testGoalWithSubcategoryAndYearlyType() {
        Subcategory subcategory = new Subcategory("Subcategory", category);
        Year targetYear = Year.of(LocalDate.now().getYear());

        Goal goal = new Goal.Builder("Goal", 800.0, GoalType.YEARLY)
                .subcategory(subcategory)
                .targetYear(targetYear)
                .build();

        assertEquals(subcategory, goal.getGoalSubcategory());
        assertEquals(LocalDate.of(targetYear.getValue(), 1, 1), goal.getTimeFrameStart());
        assertEquals(LocalDate.of(targetYear.getValue(), 12, 31), goal.getTimeFrameEnd());
        assertEquals(goal.getGoalType(), GoalType.YEARLY);
        assertEquals(goal.getTargetAmount(), 800.0);
        assertEquals(goal.getGoalName(), "Goal");
    }
}