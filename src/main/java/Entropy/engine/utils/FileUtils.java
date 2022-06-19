package Entropy.engine.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileUtils {
    public static String getFileContents(String filePath) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));

            while (reader.ready()) {
                builder.append(reader.readLine());
                builder.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }
}
