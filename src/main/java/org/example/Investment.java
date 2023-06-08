package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Investment {
    private String name;
    private double quantity;
    private  List<Double> purchasePrices;

    private List<LocalDate> purchaseDates;

    public Investment(String name, int quantity, double initialPurchasePrice, LocalDate initialPurchaseDate) {
        this.name = name;
        this.quantity = quantity;
        this.purchasePrices = new ArrayList<>();
        this.purchaseDates = new ArrayList<>();
        this.purchasePrices.add(initialPurchasePrice);
        this.purchaseDates.add(initialPurchaseDate);
    }


    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public List<Double> getPurchasePrices() {
        return purchasePrices;
    }

    public List<LocalDate> getPurchaseDates() {
        return purchaseDates;
    }

    public void addPurchase(double price, LocalDate date, double quantity) {
        purchasePrices.add(price);
        purchaseDates.add(date);
        this.quantity += quantity;
    }

    public double AveragePurchasePrice(){
        return (purchasePrices.stream().mapToDouble(Double::doubleValue).sum()) / quantity;
    }

    public LocalDate getCheapestBuyDate(){
        int cheapestBuyDateIndex = getCheapestBuyIndex();
        return purchaseDates.get(cheapestBuyDateIndex);
    }

    public double getCheapestBuyAmount(){
        int cheapestBuyDateIndex = getCheapestBuyIndex();
        return purchasePrices.get(cheapestBuyDateIndex);
    }

    private int getCheapestBuyIndex(){
        double minPrice = Double.MAX_VALUE;
        int minPriceIndex = -1;

        for (int i = 0; i < purchasePrices.size(); i++) {
            double current = purchasePrices.get(i);
            if (current < minPrice) {
                minPrice = current;
                minPriceIndex = i;
            }
        }
        return minPriceIndex;
    }

    public LocalDate getMostExpensiveBuyDate(){
        int mostExpensiveBuyDateIndex = getMostExpensiveBuyIndex();
        return purchaseDates.get(mostExpensiveBuyDateIndex);
    }

    public double getMostExpensiveBuyAmount(){
        int mostExpensiveBuyDateIndex = getMostExpensiveBuyIndex();
        return purchasePrices.get(mostExpensiveBuyDateIndex);
    }

    private int getMostExpensiveBuyIndex(){
        double maxPrice = Double.MIN_VALUE;
        int maxPriceIndex = -1;

        for (int i = 0; i < purchasePrices.size(); i++) {
            double current = purchasePrices.get(i);
            if (current > maxPrice) {
                maxPrice = current;
                maxPriceIndex = i;
            }
        }
        return maxPriceIndex;
    }

    public double getInvestmentLossGain(double actualPrice){
        double investmentPriceDelta = actualPrice - AveragePurchasePrice();
        return quantity * investmentPriceDelta;
    }

}
