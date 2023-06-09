package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    @Test
    public void testLoginUserNewUserRegistration() {
        // Arrange
        String newUsername = "john";
        String newPassword = "password";
        String filepath = "filepath";

        UserLogin userLogin = Mockito.spy(new UserLogin(filepath, newUsername, newPassword));

        // Stub the getCredentials method to return null, simulating a new user
        Mockito.doReturn(null).when(userLogin).getCredentials(newUsername);

        // Create a ByteArrayInputStream with the desired input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("yes\n".getBytes());

        // Redirect the standard input stream to the ByteArrayInputStream
        System.setIn(inputStream);

        // Act
        boolean loginResult = userLogin.loginUser();

        // Assert
        assertTrue(loginResult);
        Mockito.verify(userLogin).getCredentials(newUsername);
        Mockito.verify(userLogin).registerUser(newUsername, newPassword);

        // Restore the original input stream
        System.setIn(System.in);

        //Creating new Investment
        Investment investment = new Investment("Testing investment");

        //Adding purchases
        investment.addPurchase(10, LocalDate.of(2023, 2, 17), 25);
        investment.addPurchase(17, LocalDate.of(2023, 4, 17), 12);

        //Asserting only 2 buys have been done and user has the right amount of shares
        Assertions.assertEquals(2, investment.getNumberofBuys());
        Assertions.assertEquals(25+12, investment.getTotalNumberOfShares());

        //Asserting if gain matches the expectations
        double actualPrice = 24;
        double averagePrice = MyUtils.calculateWeightedAverage(new double[] {10, 17}, new double[] {25, 12});
        double gain = (actualPrice * 37) - (averagePrice * 37);
        Assertions.assertEquals(gain, investment.getInvestmentLossGain(actualPrice));

        //Adding sells
        investment.addSale(actualPrice, LocalDate.now(), 30);

        //Asserting if the sale was done and the user really has the right amount of stocks
        Assertions.assertEquals(1, investment.getNumberofSells());
        Assertions.assertEquals(7, investment.getTotalNumberOfShares());
        double incomeFromInvestment = ((actualPrice * 30) - (averagePrice * 30));
        Assertions.assertEquals(incomeFromInvestment, investment.getGain());

        // Delete the spied file after the test
        java.nio.file.Path path = java.nio.file.Paths.get(filepath);
        try {
            java.nio.file.Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}