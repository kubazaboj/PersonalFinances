package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        final String filepath = "src/main/resources/credentials.txt";
        System.out.println("Enter your username:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        userLogin login = new userLogin(filepath, username, password);
        if (!login.loginUser()){
            System.out.println("Login denied!");
        }else{



        }

        scanner.close();
        System.out.println("Ending program!");
    }


}