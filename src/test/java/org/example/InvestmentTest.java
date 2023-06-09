package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvestmentTest {

    Investment investment;
    double initialPurchasePrice = 10.0;
    double initialSharesBought = 100;
    @BeforeEach
    public void beforeEach(){
        investment = new Investment("Test Investment", initialSharesBought, initialPurchasePrice, LocalDate.now());
    }

    @Test
    public void testAddPurchase() {

        investment.addPurchase(15.0, LocalDate.now().plusDays(1), 50);

        assertEquals(150, investment.getTotalNumberOfShares());
        assertEquals(2, investment.getPurchasePrices().size());
        assertEquals(2, investment.getPurchaseDates().size());
    }

    @Test
    public void testAddSale() {
        investment.addPurchase(20.0, LocalDate.now(), 3);

        assertEquals(2, investment.getNumberofBuys());

        investment.addSale(15.0, LocalDate.now(), 1);

        assertEquals(2, investment.getNumberofBuys());
        assertEquals(1, investment.getNumberofSells());
    }

    @Test
    public void testGetNumBuys() {
        investment.addPurchase(10.0, LocalDate.now().plusDays(1), 4);
        investment.addPurchase(0.0, LocalDate.now().plusDays(2), 0.0);

        assertEquals(2, investment.getNumberofBuys());
    }

    @Test
    public void testGetNumSells() {
        investment.addSale(5, LocalDate.now(), 15);
        investment.addSale(8, LocalDate.now(), 15);
        assertEquals(2, investment.getNumberofSells());
    }
    @Test
    public void testAveragePurchasePriceWithoutSale() {
        investment.addPurchase(15.0, LocalDate.now().plusDays(1), 50);
        assertEquals(11.6667, investment.AveragePurchasePrice(), 0.0001);
    }

    @Test
    public void testAveragePurchasePriceWithSale() {
        investment.addPurchase(20.0, LocalDate.now().plusDays(1), 100);
        investment.addSale(20.0, LocalDate.now().plusDays(1), 50);
        assertEquals(13.3334, investment.AveragePurchasePrice(), 0.001);
    }

    @Test
    public void testGetCheapestBuyDate() {
        investment.addPurchase(8.0, LocalDate.now().plusDays(1), 50);
        investment.addPurchase(8.01, LocalDate.now().plusDays(2), 0.7);
        investment.addSale(4, LocalDate.now().plusDays(27), 1);
        assertEquals(LocalDate.now().plusDays(1), investment.getCheapestBuyDate());
    }

    @Test
    public void testGetCheapestBuyAmount() {
        investment.addPurchase(8.0, LocalDate.now().plusDays(1), 50);
        investment.addSale(4, LocalDate.now().plusDays(27), 1);
        assertEquals(8.0, investment.getCheapestBuyAmount());
    }

    @Test
    public void testGetMostExpensiveBuyDate() {
        investment.addPurchase(9.5, LocalDate.now().plusDays(1), 50);
        investment.addPurchase(10.01, LocalDate.now().plusDays(2), 0.7);
        investment.addSale(250.05, LocalDate.now().plusDays(27), 1);

        assertEquals(LocalDate.now().plusDays(2), investment.getMostExpensiveBuyDate());
    }

    @Test
    public void testGetMostExpensiveBuyAmount() {
        investment.addPurchase(15.0, LocalDate.now().plusDays(1), 50);
        investment.addSale(250.05, LocalDate.now().plusDays(27), 1);
        assertEquals(15.0, investment.getMostExpensiveBuyAmount());
    }

    @Test
    public void testGetCheapestSellDate() {
        investment.addPurchase(8.0, LocalDate.now().plusDays(1), 50);
        investment.addPurchase(8.01, LocalDate.now().plusDays(2), 0.7);
        investment.addSale(250.05, LocalDate.now().plusDays(27), 1);
        investment.addSale(125.33, LocalDate.now().plusDays(29), 1);
        assertEquals(LocalDate.now().plusDays(29), investment.getCheapestSellDate());
    }

    @Test
    public void testGetCheapestSellAmount() {
        investment.addPurchase(8.0, LocalDate.now().plusDays(1), 50);
        investment.addSale(250.05, LocalDate.now().plusDays(27), 1);
        investment.addSale(125.33, LocalDate.now().plusDays(29), 1);
        assertEquals(125.33, investment.getCheapestSellAmount());
    }

    @Test
    public void testGetMostExpensiveSellDate() {
        investment.addPurchase(9.5, LocalDate.now().plusDays(1), 50);
        investment.addPurchase(10.01, LocalDate.now().plusDays(2), 0.7);
        investment.addSale(250.05, LocalDate.now().plusDays(27), 1);
        investment.addSale(125.33, LocalDate.now().plusDays(29), 1);

        assertEquals(LocalDate.now().plusDays(27), investment.getMostExpensiveSellDate());
    }

    @Test
    public void testGetMostExpensiveSellAmount() {
        investment.addPurchase(15.0, LocalDate.now().plusDays(1), 50);
        investment.addSale(250.05, LocalDate.now().plusDays(27), 1);
        investment.addSale(125.33, LocalDate.now().plusDays(29), 1);
        assertEquals(250.05, investment.getMostExpensiveSellAmount());
    }

    @ParameterizedTest
    @CsvSource({
            "12.5",
            "10.0",
            "5,"
    })
    public void testGetInvestmentLossGain(double actualPrice) {
        investment.addPurchase(15.0, LocalDate.now().plusDays(1), 50);
        double[] allBoughtQuantities = {initialSharesBought, 50};
        double weightedAverage = calculateWeightedAverage(new double[]{initialPurchasePrice, 15.0},
                allBoughtQuantities);
        double SumSharesBought = Arrays.stream(allBoughtQuantities).sum();

        assertEquals((actualPrice - weightedAverage) * SumSharesBought, investment.getInvestmentLossGain(actualPrice), 0.001);
    }

    @Test
    public void testInvestmentTotalValue(){
        investment.addPurchase(20, LocalDate.now(), 100);
        investment.addSale(100, LocalDate.now(), 20);
        assertEquals(initialPurchasePrice * initialSharesBought, investment.getInvestmentTotalValue());
    }

    @Test
    public void testAddSaleThrowsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> {
            investment.addSale(200, LocalDate.now().plusDays(1), 10);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 20.0, 30.0, 40.0, 50.0",
            "15.7, 12.8, 17.2, 13.1, 19.4",
            "0.1, 2.4, 18,88, 25.1, 6.9"
    })
    public void testInvestmentMethods(double purchasePrice1, double purchasePrice2, double purchasePrice3,
                                      double purchasePrice4, double purchasePrice5) {
        double[] purchasePrices = {purchasePrice1, purchasePrice2, purchasePrice3,
        purchasePrice4, purchasePrice5, initialPurchasePrice};

        investment.addPurchase(purchasePrices[0], LocalDate.now().plusDays(1), 100);
        investment.addPurchase(purchasePrices[1], LocalDate.now().plusDays(2), 50);
        investment.addPurchase(purchasePrices[2], LocalDate.now().plusDays(3), 30);
        investment.addPurchase(purchasePrices[3], LocalDate.now().plusDays(4), 20);
        investment.addPurchase(purchasePrices[4], LocalDate.now().plusDays(5), 10);

        double weightedAverage = calculateWeightedAverage(purchasePrices,
                new double[]{100, 50, 30, 20, 10, initialSharesBought});

        assertEquals(310, investment.getTotalNumberOfShares());
        assertEquals(Arrays.stream(purchasePrices).min().orElse(0.0), investment.getCheapestBuyAmount());
        assertEquals(Arrays.stream(purchasePrices).max().orElse(Double.MAX_VALUE), investment.getMostExpensiveBuyAmount());
        assertEquals(weightedAverage, investment.AveragePurchasePrice(), 0.001);
    }


    private double calculateWeightedAverage(double[] values, double[] weights) {
        if (values.length != weights.length) {
            throw new IllegalArgumentException("Values and weights arrays must have the same length.");
        }

        double sum = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i] * weights[i];
            totalWeight += weights[i];
        }

        if (totalWeight == 0.0) {
            throw new IllegalArgumentException("Total weight must be greater than zero.");
        }

        return sum / totalWeight;
    }
}