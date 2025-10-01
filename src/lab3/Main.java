package lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static HashMap<String, String> checkFile() throws FileReadException, InvalidFileFormatException {
        HashMap<String, String> dictionary = new HashMap<>();

        try (BufferedReader file = new BufferedReader(new FileReader("src/lab3/dictionary.txt"))) {
            String line;
            int lineNumber = 0;

            while ((line = file.readLine()) != null){
                lineNumber++;
                String[] parts = line.split("\\|");

                if (parts.length != 2){
                    throw new InvalidFileFormatException("Invalid on line " + lineNumber + ": " + line);
                }

                String english = parts[0].trim().toLowerCase();
                String russian = parts[1].trim();

                dictionary.put(english, russian);
            }

            return dictionary;

        }
        catch (IOException e){
            throw new FileReadException("Cannot read file. Eror: ", e);
        }
    }

    public static String translateText (HashMap<String, String> dictionary, String text) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (i < words.length) {
            String longestMatch =  null;
            String tranlation  = null;

            for (int k = i + 1; k <= words.length; k++) {
                String phrase = String.join(" ", Arrays.copyOfRange(words, i, k)).toLowerCase();

                if (dictionary.containsKey(phrase) ){
                    longestMatch = phrase;
                    tranlation = dictionary.get(phrase);
                }
            }

            if (tranlation != null) {
                result.append(tranlation).append(" ");
                i += longestMatch.split(" ").length;
            }
            else {
                result.append(words[i]).append(" ");
                i++;
            }
        }

        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter text : ");
            String word = scanner.nextLine();

            var dictionary = checkFile();

            String translation = translateText(dictionary, word);

            System.out.println("Translate: " + translation);
        }
        catch (FileReadException e) {
            System.out.println("File error: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Details: " + e.getCause().getMessage());
            }
        }
        catch (InvalidFileFormatException e) {
            System.out.println("Invalid file format: " + e.getMessage());
        }
    }
}