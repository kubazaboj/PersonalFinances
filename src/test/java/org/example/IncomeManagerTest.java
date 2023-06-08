package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncomeManagerTest {

    private IncomeManager incomeManager;

    @Mock
    private Income income;

    @Mock
    private Budget budget;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        List<Income> incomes = new ArrayList<>();
        incomes.add(income);
        incomeManager = new IncomeManager(budget);
    }

    @Test
    public void testAddIncome() {
        // Create the necessary dependencies
        Budget budget = mock(Budget.class);
        Subcategory subcategory = mock(Subcategory.class);

        // Create a new instance of Income with the required arguments
        double incomeAmount = 100.0;
        Income newIncome = new Income("Description", incomeAmount, LocalDate.now(), subcategory);

        // Set up any necessary behavior on the mocks

        // Invoke the method under test
        IncomeManager incomeManager = new IncomeManager(budget);
        incomeManager.addIncome(newIncome);

        // Perform assertions
        assertEquals(1, incomeManager.getAllIncomes().size());
        assertEquals(incomeAmount, incomeManager.getTotalIncome());
    }

    @Test
    public void testRemoveIncome() {
        double incomeAmount = 100.0;

        when(income.getAmount()).thenReturn(incomeAmount);

        incomeManager.addIncome(income);
        incomeManager.removeIncome(income);

        assertEquals(0, incomeManager.getAllIncomes().size());
        assertEquals(0.0, incomeManager.getTotalIncome());
    }

    @Test
    public void testGetTotalIncomeForDay() {
        double incomeAmount = 50.0;
        LocalDate incomeDate = LocalDate.now();

        when(income.getAmount()).thenReturn(incomeAmount);
        when(income.getDate()).thenReturn(incomeDate);

        double totalIncome = incomeManager.getTotalIncomeForDay(incomeDate);

        assertEquals(incomeAmount, totalIncome);
    }

    @Test
    public void testGetTotalIncomeForWeek() {
        double incomeAmount = 50.0;
        LocalDate startOfWeek = LocalDate.now().minusDays(3);
        LocalDate endOfWeek = LocalDate.now();

        when(income.getAmount()).thenReturn(incomeAmount);
        when(income.getDate()).thenReturn(startOfWeek, endOfWeek);

        double totalIncome = incomeManager.getTotalIncomeForWeek(startOfWeek, endOfWeek);

        assertEquals(incomeAmount * 2, totalIncome);
    }

    @Test
    public void testGetTotalIncomeForMonth() {
        double incomeAmount = 50.0;
        LocalDate month = LocalDate.now();

        when(income.getAmount()).thenReturn(incomeAmount);
        when(income.getDate()).thenReturn(month);

        double totalIncome = incomeManager.getTotalIncomeForMonth(month);

        assertEquals(incomeAmount, totalIncome);
    }

    @Test
    public void testGetTotalIncomeForYear() {
        double incomeAmount = 50.0;
        int year = LocalDate.now().getYear();

        when(income.getAmount()).thenReturn(incomeAmount);
        when(income.getDate()).thenReturn(LocalDate.of(year, 1, 1));

        double totalIncome = incomeManager.getTotalIncomeForYear(year);

        assertEquals(incomeAmount, totalIncome);
    }

    // Add more test methods for other functionality as needed
}