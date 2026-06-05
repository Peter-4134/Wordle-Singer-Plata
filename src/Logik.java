import javafx.scene.control.TextField;

public class Logik {
    String word;

    /**
     * Überprüft die richtigkeit eines wortes
     * @param word: W
     */

    public Logik(String word) {
        this.word = word;
    }

    /**
     * überprüft ein TextField array
     * @param input ein Textfield aus 5 boxencdas überprüft werden soll
     * @return ein array mit 5 stellen, jeweils grün(g), gelb(y) oder rot(r)
     */

    public char[] prüfen(TextField[] input) {

        char[] test = word.toUpperCase().toCharArray();
        char[] erg = new char[input.length];

        // Leere Felder abfangen
        for (TextField f : input) {
            if (f.getText() == null || f.getText().isEmpty()) return new char[1];
        }

        for (int i = 0; i < test.length; i++) {
            if (test[i] == input[i].getText().charAt(0)) {
                erg[i] = 'g';
                test[i] = '!';
            }
        }

        for (int i = 0; i < test.length; i++) {
            if (erg[i] != 'g') {
                erg[i] = 'r';
                for (int j = 0; j < test.length; j++) {
                    if (input[i].getText().charAt(0) == test[j]) {
                        erg[i] = 'y';
                        test[j] = '!';
                    }
                }
            }
        }
        return erg;
    }
}
