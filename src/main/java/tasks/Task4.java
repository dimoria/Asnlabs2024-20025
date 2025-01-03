package tasks;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class Task4 {


    public static void processLargeFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {

            lines.filter(line -> line.contains("target"))
                    .forEach(line -> {

                        System.out.println("Processing: " + line);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "Smth.txt";
        processLargeFile(filePath);
    }
}
