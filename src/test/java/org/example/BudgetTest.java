package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

public class BudgetTest {

    Budget budget = new Budget(100.0, YearMonth.now());

    @Test
    public void testIncreaseActualBudget() {


        budget.increaseActualBudget(50.0);
        double actualBudget = budget.getActualBudget();

        Assertions.assertEquals(50.0, actualBudget);
    }

    @Test
    public void testDecreaseActualBudget() {
        budget.increaseActualBudget(75.0);

        budget.decreaseActualBudget(25.0);
        double actualBudget = budget.getActualBudget();

        Assertions.assertEquals(50.0, actualBudget);
    }

    @Test
    public void testIncreaseAllocatedBudget() {

        budget.increaseAllocatedBudget(50.0);
        double allocatedBudget = budget.getAllocatedBudget();

        Assertions.assertEquals(150.0, allocatedBudget);
    }

    @Test
    public void testDecreaseAllocatedBudget() {

        budget.decreaseAllocatedBudget(25.0);
        double allocatedBudget = budget.getAllocatedBudget();

        Assertions.assertEquals(75.0, allocatedBudget);
    }

    @Test
    public void testSetAllocatedBudget() {
        Budget budget = new Budget(100.0, YearMonth.of(2022, 5));

        budget.setAllocatedBudget(150.0);
        double allocatedBudget = budget.getAllocatedBudget();

        Assertions.assertEquals(150.0, allocatedBudget);
    }

    @Test
    public void testSetYearMonth() {
        YearMonth newYearMonth = YearMonth.of(2023, 5);

        budget.setYearMonth(newYearMonth);
        YearMonth yearMonth = budget.getYearMonth();

        Assertions.assertEquals(newYearMonth, yearMonth);
    }
}
