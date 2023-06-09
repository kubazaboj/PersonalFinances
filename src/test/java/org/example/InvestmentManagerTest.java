package org.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;

class InvestmentManagerTest {
    private InvestmentManager investmentManager;

    @BeforeEach
    public void beforeEach() {
        investmentManager = new InvestmentManager();
    }

    @Test
    public void testAddInvestment() {
        Investment investment = mock(Investment.class);
        investmentManager.addInvestment(investment);

        List<Investment> investments = investmentManager.getAllInvestments();

        assertEquals(1, investments.size());
        assertEquals(investment, investments.get(0));
    }

    @Test
    public void testRemoveInvestment() {
        Investment investment = mock(Investment.class);
        investmentManager.addInvestment(investment);
        assertEquals(1, investmentManager.getAllInvestments().size());

        investmentManager.removeInvestment(investment);
        assertEquals(0, investmentManager.getAllInvestments().size());
    }

    @Test
    public void testGetInvestmentByName() {
        String investmentName = "Test Investment";
        Investment investment = mock(Investment.class);
        when(investment.getName()).thenReturn(investmentName);

        investmentManager.addInvestment(investment);

        Investment retrievedInvestment = investmentManager.getInvestmentByName(investmentName);

        assertEquals(investment, retrievedInvestment);
    }
}