package org.example;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class Goal {
    private String name;
    private double targetAmount;
    private GoalType goalType;
    private Category category;
    private Subcategory subcategory;
    private LocalDate targetDay;
    private Week targetWeek;
    private YearMonth targetMonth;
    private Year targetYear;

    private Goal(Builder builder) {
        this.name = builder.name;
        this.targetAmount = builder.targetAmount;
        this.goalType = builder.goalType;
        this.category = builder.category;
        this.subcategory = builder.subcategory;
        this.targetDay = builder.targetDay;
        this.targetWeek = builder.targetWeek;
        this.targetMonth = builder.targetMonth;
        this.targetYear = builder.targetYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    public Category getGoalCategory() {
        return category;
    }

    public void setGoalCategory(Category category) {
        this.category = category;
    }

    public Subcategory getGoalSubcategory() {
        return subcategory;
    }

    public void setGoalSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public LocalDate getGoalDay(){
        return targetDay;
    }

    public long getWeekGoalDaysRemaining(){
        return ChronoUnit.DAYS.between(LocalDate.now(), targetWeek.getEndDate());
    }

    public long getMonthGoalDaysRemaining(){
        return ChronoUnit.DAYS.between(LocalDate.now(), targetMonth.atEndOfMonth());
    }
    public long getYearGoalDaysRemaining(){
        return ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(Year.now().getValue(), 12, 31));

    }

    public LocalDate getTimeFrameStart() {
        return switch (goalType) {
            case DAILY -> targetDay;
            case WEEKLY -> targetWeek.getStartDate();
            case MONTHLY -> LocalDate.of(1, 1, 1);
            case YEARLY -> LocalDate.of(targetYear.getValue(), 1, 1);
            default -> throw new IllegalArgumentException("Unsupported time frame: " + goalType);
        };
    }

    public LocalDate getTimeFrameEnd() {
        return switch (goalType) {
            case DAILY -> targetDay;
            case WEEKLY -> targetWeek.getEndDate();
            case MONTHLY -> targetMonth.atEndOfMonth();
            case YEARLY -> LocalDate.of(targetYear.getValue(), 12, 31);
            default -> throw new IllegalArgumentException("Unsupported time frame: " + goalType);
        };
    }

    public static class Builder {
        private String name;
        private double targetAmount;
        private GoalType goalType;
        private Category category;
        private Subcategory subcategory;
        private LocalDate targetDay;
        private Week targetWeek;
        private YearMonth targetMonth;
        private Year targetYear;

        public Builder(String name, double targetAmount, GoalType goalType) {
            this.name = name;
            this.targetAmount = targetAmount;
            this.goalType = goalType;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder subcategory(Subcategory subcategory) {
            this.subcategory = subcategory;
            return this;
        }

        public Builder targetDay(LocalDate targetDay) {
            this.targetDay = targetDay;
            return this;
        }

        public Builder targetWeek(Week targetWeek) {
            this.targetWeek = targetWeek;
            return this;
        }

        public Builder targetMonth(YearMonth targetMonth) {
            this.targetMonth = targetMonth;
            return this;
        }

        public Builder targetYear(Year targetYear) {
            this.targetYear = targetYear;
            return this;
        }

        public Goal build() {

            if (category != null && subcategory != null) {
                throw new IllegalArgumentException("A goal can have either a category or a subcategory, but not both.");
            }

            return new Goal(this);
        }
    }


}
