package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeekTest {
    @Test
    public void testGetStartDate() {
        LocalDate startDate = LocalDate.now();
        Week week = new Week(startDate);

        // Verify that the start date matches the provided value
        assertEquals(startDate, week.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        LocalDate startDate = LocalDate.now();
        Week week = new Week(startDate);

        // Calculate the expected end date as the start date plus 6 days
        LocalDate expectedEndDate = startDate.plusDays(6);

        // Verify that the end date matches the expected value
        assertEquals(expectedEndDate, week.getEndDate());
    }

}