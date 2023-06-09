package org.example;

import java.io.File;
import java.io.IOException;

public class MyUtils {

    public static void createFileIfNotExists(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static double calculateWeightedAverage(double[] values, double[] weights) {
        if (values.length != weights.length) {
            throw new IllegalArgumentException("Values and weights arrays must have the same length.");
        }

        double sum = 0.0;
        double totalWeight = 0.0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i] * weights[i];
            totalWeight += weights[i];
        }

        if (totalWeight == 0.0) {
            throw new IllegalArgumentException("Total weight must be greater than zero.");
        }

        return sum / totalWeight;
    }

}
