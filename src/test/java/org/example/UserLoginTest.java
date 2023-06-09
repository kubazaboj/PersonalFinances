package org.example;

import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserLoginTest {


    String username = "john";
    String password = "newpassword";
    String userData = username + ":" + BCrypt.hashpw(password, BCrypt.gensalt());

    @Test
    public void testLoginUserSuccessfulLogin() throws IOException {
        // Create a temporary file for testing
        File tempFile = createTempFileWithUserData();
        BufferedReader mockBufferedReader = mock(BufferedReader.class);
        UserLogin login = new UserLogin(tempFile.getAbsolutePath(), username, password);



        try {
            // Mock the behavior of BufferedReader.readLine() to return the user's credentials
            when(mockBufferedReader.readLine()).thenReturn(userData);

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

        BufferedReader mockBufferedReader = mock(BufferedReader.class);
        UserLogin login = new UserLogin(tempFile.getAbsolutePath(), username, "wrongpassword");

        try {
            // Mock the behavior of BufferedReader.readLine() to return the user's credentials
            when(mockBufferedReader.readLine()).thenReturn(userData);

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

        // Create a ByteArrayInputStream with the desired input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("yes\n".getBytes());

        // Redirect the standard input stream to the ByteArrayInputStream
        System.setIn(inputStream);

        // Act
        boolean result = userLogin.loginUser();

        // Assert
        assertTrue(result);
        Mockito.verify(userLogin).getCredentials(newUsername);
        Mockito.verify(userLogin).registerUser(newUsername, newPassword);

        // Restore the original input stream
        System.setIn(System.in);

        // Delete the spied file after the test
        java.nio.file.Path path = java.nio.file.Paths.get(filepath);
        try {
            java.nio.file.Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegistrationNotWanted() {
        // Arrange
        String username = "john";
        String password = "password";
        String filepath = "filepath";

        UserLogin userLogin = Mockito.spy(new UserLogin(filepath, username, password));

        // Stub the getCredentials method to return null, simulating a new user
        Mockito.doReturn(null).when(userLogin).getCredentials(username);

        // Create a ByteArrayInputStream with the desired input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("no\n".getBytes());

        // Redirect the standard input stream to the ByteArrayInputStream
        System.setIn(inputStream);

        // Act
        assertThrows(RuntimeException.class, userLogin::loginUser);

        // Assert
        Mockito.verify(userLogin).getCredentials(username);
        Mockito.verify(userLogin, Mockito.never()).registerUser(Mockito.anyString(), Mockito.anyString());

        // Restore the original input stream
        System.setIn(System.in);

        // Delete the spied file after the test
        java.nio.file.Path path = java.nio.file.Paths.get(filepath);
        try {
            java.nio.file.Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
