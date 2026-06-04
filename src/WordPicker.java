import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WordPicker {

    /**
     * Liest eine Liste von Wörtern aus einer Datei und gibt ein zufällig ausgewähltes Wort in Großbuchstaben zurück.
     * Die Datei wird unter "src/Words.txt" erwartet und soll pro Zeile ein Wort enthalten.
     * Falls beim Lesen der Datei ein Fehler auftritt, wird stattdessen ein leerer String zurückgegeben.
     *
     * @return Ein zufällig ausgewähltes Wort aus der Datei in Großbuchstaben oder ein leerer String im Fehlerfall.
     */
    public static String getRandomWord() {
        try {
            List<String> words = Files.readAllLines(Path.of("src/Words.txt"));
            int randomIndex = (int) (Math.random() * words.size());
            return words.get(randomIndex).toUpperCase();
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei " + e.getMessage());
        }
        return "";
    }
}

