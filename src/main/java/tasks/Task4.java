package tasks;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class Task4 {

    public static void processLargeFile(String filePath) {
        Path path = Paths.get(filePath);


        if (!Files.exists(path)) {
            System.out.println("File not found: " + filePath);
            System.out.println("Absolute path: " + path.toAbsolutePath());  // Додаємо абсолютний шлях для перевірки
            return;
        }

        try (Stream<String> lines = Files.lines(path)) {

            lines.filter(line -> line.contains("target"))
                    .forEach(line -> {

                        System.out.println("Processing: " + line);
                    });
        } catch (IOException e) {

            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String filePath = "Smth.txt";
        processLargeFile(filePath);
    }
}
