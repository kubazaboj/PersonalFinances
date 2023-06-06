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
}
