package org.example;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginTest {
    private static final String testFilepath = "src/main/resources/test-credentials.txt";
    private static final String testUsername = "testUser";
    private static final String testPassword = "testPassword";

    private userLogin login;

    @BeforeEach
    void beforeEach() {
        MyUtils.createFileIfNotExists(testFilepath);
        login = new userLogin(testFilepath, testUsername, testPassword);

    }

    @AfterEach
    void afterEach() {
        // Clean up test file after each test
        File file = new File(testFilepath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void loginUser_ValidCredentials_LoginSuccessful() {
        // Arrange
        registerTestUser();

        // Act
        boolean result = login.loginUser();

        // Assert
        assertTrue(result, "Login should be successful");
    }

    @Test
    void loginUser_NewUser_RegistrationSuccessful() {
        // Arrange

        // Act
        boolean result = login.loginUser();

        // Assert
        assertTrue(result, "Login should be successful for newly registered user");
    }

    @Test
    void loginUser_ExistingUser_RegistrationSkipped() {
        // Arrange
        registerTestUser();

        // Act
        boolean result = login.loginUser();

        // Assert
        assertTrue(result, "Login should be successful for existing user without registration");
    }


    @Test
    void loginUser_InvalidCredentials_LoginFailed() {
        // Arrange
        registerTestUser();
        login = new userLogin(testFilepath, testUsername, "wrongPassword");

        // Act
        boolean result = login.loginUser();

        // Assert
        assertFalse(result, "Login should fail with wrong password");
    }

    private void registerTestUser() {
        MyUtils.createFileIfNotExists(testFilepath);
        String hashedPassword = BCrypt.hashpw(testPassword, BCrypt.gensalt());
        try (FileWriter writer = new FileWriter(testFilepath)) {
            writer.write(testUsername + ":" + hashedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}