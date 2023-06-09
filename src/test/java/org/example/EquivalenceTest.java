package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EquivalenceTest {
    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "-1, 1",
            "-1, 100",
            "-1, 1000",
            "0, -1",
            "0, 0",
            "0, 1",
            "0, 100",
            "0, 1000",
            "10, -1",
            "10, 0",
            "10, 1",
            "10, 100",
            "10, 1000",
    })
    public void testAddSaleThrowsIllegalArgumentException(double price, double quantity) {
        //Setting up the investment
        Investment investment = new Investment("Testing Investment");
        double initialPurchasePrice = 10.0;
        double initialSharesBought = 100;
        investment.addPurchase(initialPurchasePrice, LocalDate.now(), initialSharesBought);

        //Assertion of exception
        if ((quantity <= initialSharesBought && quantity > 0 && price >= 0)){
            assertDoesNotThrow(() -> investment.addSale(price, LocalDate.now(), quantity));
        }
        else{
            assertThrows(IllegalArgumentException.class, () -> investment.addSale(price, LocalDate.now().plusDays(1), quantity));
        }
    }

}