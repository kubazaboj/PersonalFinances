package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

public class userLogin {

    private final String filepath;
    private final String username;

    private final String password;

    public userLogin(String filepath, String username, String password) {
        this.filepath = filepath;
        this.username = username;
        this.password = password;
    }
    public boolean loginUser(){
        if (checkRegistration(this.username, this.password)){
            String[] credentials = getCredentials(username);
            assert credentials != null;
            if (BCrypt.checkpw(password, credentials[1])){
                System.out.println("Login successful");
                return true;
            }
            System.out.println("Wrong password written");
            return false; //If passwords do not match. return false
        }
        return true; //For newly registered users, assert true
    }

    private boolean checkRegistration(String typedUsername,
                                      String typedPassword){
        String[] credentials = getCredentials(typedUsername);
        if (credentials != null){
            if (credentials.length == 2 && credentials[0].equals
                    (typedUsername)) {
                return true;
            }
        }
            System.out.println("Registering new user");
            registerUser(typedUsername, typedPassword); //Username did not exist, register new user
            return false;
    }
    private String[] getCredentials(String username){
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username)) {
                    return parts; // Return the credentials,
                    // hashed username and password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Username not found or error occurred, returning null
    }

    private void registerUser(String username, String password) {
        MyUtils.createFileIfNotExists(this.filepath);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try (FileWriter writer = new FileWriter(this.filepath)) {
            writer.write(username + ":" + hashedPassword);
            System.out.println("Registration successful");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Registration unsuccessful");
        }
    }
}
