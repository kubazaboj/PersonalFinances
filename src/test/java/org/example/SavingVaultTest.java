package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SavingVaultTest {
    SavingVault savingVault;

    @BeforeEach
    public void beforeEach(){
        savingVault = new SavingVault("Vacation", 5000.0);

    }

    @ParameterizedTest
    @CsvSource({
            "2500.0, 2500.0",
            "1000.0, 1000.0",
            "500.0, 500.0"
    })
    public void testDepositPositiveAmount(double amount, double expectedSavings) {
        // Act
        savingVault.deposit(amount);

        // Assert
        assertEquals(expectedSavings, savingVault.getSavings());
    }

    @ParameterizedTest
    @CsvSource({
            "0.0",
            "-100.0",
            "-500.0"
    })
    public void testDepositInvalidAmount(double amount) {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            savingVault.deposit(amount);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "1500.0, 1500.0",
            "500.0, 2500.0",
            "1000.0, 2000.0"
    })
    public void testWithdrawPositiveAmount(double amount, double expectedSavings) {

        savingVault.deposit(3000);


        savingVault.withdraw(amount);
        double savings = savingVault.getSavings();


        assertEquals(expectedSavings, savingVault.getSavings());
    }

    @ParameterizedTest
    @CsvSource({
            "0.0",
            "-100.0",
            "-500.0"
    })
    public void testWithdrawInvalidAmount(double amount) {
        savingVault.deposit(3000.0);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            savingVault.withdraw(amount);
        });
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        savingVault.deposit(3000.0);
        double amount = 4000.0;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            savingVault.withdraw(amount);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "4999",
            "5000",
            "5001"
    })
    public void testDepositFulfillsDream(double initialDeposit) {

        // Redirect System.out to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Act
            savingVault.deposit(initialDeposit);

            // Assert
            String expectedMessage = "Money for dream fulfilled!";
            if (initialDeposit < 5000){
                assertFalse(outputStream.toString().contains(expectedMessage));
            }else{
                assertTrue(outputStream.toString().contains(expectedMessage));
            }
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }

}