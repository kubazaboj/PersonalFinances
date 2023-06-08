package org.example;

import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UserLoginTest {


    String username = "john";
    String password = "newpassword";
    String userData = username + ":" + BCrypt.hashpw(password, BCrypt.gensalt());

    @Test
    public void testLoginUserSuccessfulLogin() throws IOException {
        // Create a temporary file for testing
        File tempFile = createTempFileWithUserData();
        BufferedReader mockBufferedReader = Mockito.mock(BufferedReader.class);
        UserLogin login = new UserLogin(tempFile.getAbsolutePath(), username, password);



        try {
            // Mock the behavior of BufferedReader.readLine() to return the user's credentials
            Mockito.when(mockBufferedReader.readLine()).thenReturn(userData);

            // Call the method under test, passing the mocked dependencies
            boolean result = login.loginUser();

            // Assert the result
            assertTrue(result);
        } finally {
            // Delete the temporary file after the test
            tempFile.delete();
        }
    }

    @Test
    public void testLoginUserFailedLogin() throws IOException {
        // Create a temporary file for testing
        File tempFile = createTempFileWithUserData();

        BufferedReader mockBufferedReader = Mockito.mock(BufferedReader.class);
        UserLogin login = new UserLogin(tempFile.getAbsolutePath(), username, "wrongpassword");

        try {
            // Mock the behavior of BufferedReader.readLine() to return the user's credentials
            Mockito.when(mockBufferedReader.readLine()).thenReturn(userData);

            // Call the method under test, passing the mocked dependencies
            boolean result = login.loginUser();

            // Assert the result
            assertFalse(result);
        } finally {
            // Delete the temporary file after the test
            tempFile.delete();
        }
    }

    private File createTempFileWithUserData() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(userData);
        }

        return tempFile;
    }

    @Test
    public void testLoginUserNewUserRegistration() {
        // Arrange
        String newUsername = "jane";
        String newPassword = "password123";
        String filepath = "filepath";

        UserLogin userLogin = Mockito.spy(new UserLogin(filepath, newUsername, newPassword));

        // Stub the getCredentials method to return null, simulating a new user
        Mockito.doReturn(null).when(userLogin).getCredentials(newUsername);

        // Act
        boolean result = userLogin.loginUser();

        // Assert
        assertTrue(result);
        Mockito.verify(userLogin).getCredentials(newUsername);
        Mockito.verify(userLogin).registerUser(newUsername, newPassword);

        // Delete the spied file after the test
        java.nio.file.Path path = java.nio.file.Paths.get(filepath);
        try {
            java.nio.file.Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
