package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Investment {
    private String name;
    private  List<Double> purchasePrices;
    private List<Double> purchaseShares;

    private List<LocalDate> purchaseDates;

    public Investment(String name, double initialSharesBought, double initialPurchasePrice, LocalDate initialPurchaseDate) {
        this.name = name;
        this.purchasePrices = new ArrayList<>();
        this.purchaseDates = new ArrayList<>();
        this.purchaseShares = new ArrayList<>();
        this.purchasePrices.add(initialPurchasePrice);
        this.purchaseDates.add(initialPurchaseDate);
        this.purchaseShares.add(initialSharesBought);
    }


    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getPurchaseShares() {
        return purchaseShares;
    }

    public double getTotalNumberOfShares() {return purchaseShares.stream().mapToDouble(Double::doubleValue).sum();}

    public List<Double> getPurchasePrices() {
        return purchasePrices;
    }

    public List<LocalDate> getPurchaseDates() {
        return purchaseDates;
    }

    public void addPurchase(double price, LocalDate date, double quantity) {
        purchasePrices.add(price);
        purchaseDates.add(date);
        purchaseShares.add(quantity);
    }

    public void addSale(double price, LocalDate date, double quantity){
        if (quantity * price > getInvestmentTotalValue()){
            throw new IllegalArgumentException("You cannot sell more than is your overall investment value");
        }
        purchasePrices.add(-1 * price);
        purchaseDates.add(date);
        purchaseShares.add(-1 * quantity);
    }

    public int getNumberofBuys(){
        int numberBuys = 0;
        for (double purchasePrice:purchasePrices) {
            if (purchasePrice > 0){
                numberBuys += 1;
            }
        }
        return numberBuys;
    }
    public int getNumberofSells(){
        int numberSells = 0;
        for (double purchasePrice:purchasePrices) {
            if (purchasePrice < 0){
                numberSells += 1;
            }
        }
        return numberSells;
    }

    public double AveragePurchasePrice(){
        double sumAllPrices = 0.0;
        if (purchasePrices.size() != purchaseShares.size()){
            System.err.println("Cannot calculate average purchase price, not every purchase has price or number of shares added");
            return sumAllPrices;
        }
        for (int i = 0; i < purchasePrices.size(); i++) {
            if (purchasePrices.get(i) < 0 || purchaseShares.get(i) < 0){
                sumAllPrices -= purchasePrices.get(i) * purchaseShares.get(i);

            }else {
                sumAllPrices += purchasePrices.get(i) * purchaseShares.get(i);
            }
        }
        return sumAllPrices / getTotalNumberOfShares();
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
            if (current < minPrice && current > 0) {
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
            if (current > maxPrice && current > 0) {
                maxPrice = current;
                maxPriceIndex = i;
            }
        }
        return maxPriceIndex;
    }

    public LocalDate getCheapestSellDate(){
        int cheapestBuyDateIndex = getCheapestSellIndex();
        return purchaseDates.get(cheapestBuyDateIndex);
    }

    public double getCheapestSellAmount(){
        int cheapestBuyDateIndex = getCheapestSellIndex();
        return purchasePrices.get(cheapestBuyDateIndex) * (-1);
    }

    private int getCheapestSellIndex(){
        double minPrice = Double.MAX_VALUE * (-1);
        int minPriceIndex = -1;

        for (int i = 0; i < purchasePrices.size(); i++) {
            double current = purchasePrices.get(i);
            if (current > minPrice && current < 0) {
                minPrice = current;
                minPriceIndex = i;
            }
        }
        return minPriceIndex;
    }

    public LocalDate getMostExpensiveSellDate(){
        int mostExpensiveBuyDateIndex = getMostExpensiveSellIndex();
        return purchaseDates.get(mostExpensiveBuyDateIndex);
    }

    public double getMostExpensiveSellAmount(){
        int mostExpensiveBuyDateIndex = getMostExpensiveSellIndex();
        return purchasePrices.get(mostExpensiveBuyDateIndex) * (-1);
    }

    private int getMostExpensiveSellIndex(){
        double maxPrice = Double.MAX_VALUE;
        int maxPriceIndex = -1;

        for (int i = 0; i < purchasePrices.size(); i++) {
            double current = purchasePrices.get(i);
            if (current < maxPrice && current < 0) {
                maxPrice = current;
                maxPriceIndex = i;
            }
        }
        return maxPriceIndex;
    }

    public double getInvestmentTotalValue(){
        return getTotalNumberOfShares() * AveragePurchasePrice();
    }

    public double getInvestmentLossGain(double actualPrice){
        double investmentPriceDelta = actualPrice - AveragePurchasePrice();
        return getTotalNumberOfShares() * investmentPriceDelta;
    }

}
