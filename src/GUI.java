import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        String word = WordPicker.getRandomWord();
        System.out.println(word);
        logik = new Logik(word);
        launch(args);
    }

    TextField[][] boxes; // so kann man auf boxen zugreifen
    int counter = 0; // wie viele versuche es gab
    int currentDifficulty = 0;
    static Logik logik; // zur Überprüfung der Richtigkeit
    User user = new User(null, 0, 0, 0); //Der User der Spielt, seine Attribute werden verändert

    /**
     * Öffnet das Fenster, um den Benutzer und den Schwierigkeitsgrad auszuwählen.
     */
    @Override
    public void start(Stage stage) throws Exception {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15));
        hBox.setSpacing(15);

        Button easy = new Button("einfach");
        Button medium = new Button("mittel");
        Button hard = new Button("schwer");
        Platform.runLater(() -> hBox.requestFocus()); //damit das Textfeld nicht gleich ausgewählt wird und man den Prompttext nicht sieht

        hBox.getChildren().addAll(easy, medium, hard);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));

        TextField username = new TextField();
        username.setPromptText("Benutzername:");

        vBox.getChildren().addAll(username, hBox);
        Scene scene = new Scene(vBox, 250, 150);

        easy.setOnAction(event -> {
            if (manageUser(username.getText())) game(1, stage);
        });

        medium.setOnAction(event -> {
            if (manageUser(username.getText())) game(0, stage);
        });

        hard.setOnAction(event -> {
            if (manageUser(username.getText())) game(-1, stage);
        });


        stage.setTitle("start");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Das Fenster in dem das Spiel stattfindet
     *
     * @param difficulty: Schwierigkeitsgrad, verändert Rateversuche
     */

    public void game(int difficulty, Stage stage) {
        this.currentDifficulty = difficulty;

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);

        int rows = 6 + difficulty;
        int cols = 5;

        TextField[][] boxes = new TextField[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField box = new TextField();

                box.setPrefSize(60, 60);

                box.setStyle("-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: #565758; " +
                        "-fx-border-width: 2px; ");

                int finalI = i;

                box.setTextFormatter(new TextFormatter<>(change -> {
                    int a = finalI;
                    if (a == counter && change.getControlNewText().length() <= 1 && change.getText().chars().allMatch(c -> Character.isLetter(c))) {
                        change.setText(change.getText().toUpperCase());
                        return change;
                    }
                    return null;
                }));

                KeyEvent tab = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.TAB, false, false, false, false);

                box.textProperty().addListener(c -> {
                    if (box.getText().length() == 1) {
                        box.fireEvent(tab);
                    }
                });

                gridPane.add(box, j, i);

                boxes[i][j] = box;


            }
        }

        Button countButton = new Button("Prüfen");
        countButton.setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-padding: 8px 15px; " +
                        "-fx-background-color: #538d4e; " + // grün, passend zum Wordle-Theme
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5px;"
        );

        countButton.setOnAction(event -> {
            char[] c = logik.prüfen(boxes[counter]);
            if (c.length == 1) {
                counter--;
            } else {
                changeBoxColor(boxes[counter], c);
            }

            if (isWin(c)) {
                countButton.setDisable(true); //Prüfen Button deaktivieren
                showEndDialog("Gewonnen! Du hast " + (counter + 1) + " Versuch(e) gebraucht.", stage, true);
            } else if (counter == rows - 1) {
                countButton.setDisable(true); //Prüfen Button deaktivieren
                showEndDialog("Verloren! Das Wort war: " + logik.word, stage, false);
            }

            counter++;
        });


        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane, countButton);
        Scene scene = new Scene(root, 500, 500 + 60 * difficulty);

        root.setStyle("-fx-background-color: #d1d1d1;");


        stage.setTitle("Wordle");
        stage.setScene(scene);
        stage.show();

        //Um mit Enter Prüfen zu können
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                countButton.fire();
            }
        });

    }


    private boolean isWin(char[] c) {
        for (char ch : c) {
            if (ch != 'g') return false;
        }
        return true;
    }

    /**
     * Zeigt dem Benutzer einen Dialog zum Spielende an, der eine Nachricht sowie die Möglichkeit enthält,
     * das Spiel neu zu starten.
     *
     * @param message Die im Dialog angezeigte Nachricht. Wird normalerweise verwendet,
     *                um das Spielergebnis mitzuteilen.
     * @param stage   Das Hauptfenster der Anwendung, das als Besitzer des Dialogs dient.
     */

    private void showEndDialog(String message, Stage stage, boolean gewonnen) {
        Stage dialog = new Stage();
        dialog.initOwner(stage);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 18px;");

        Button restart = new Button("Nochmal spielen");
        restart.setOnAction(e -> {
            dialog.close();
            counter = 0; // Counter zurücksetzen
            String newWord = WordPicker.getRandomWord();
            logik = new Logik(newWord);
            game(currentDifficulty, stage); // neues Spiel starten
        });

        VBox vBox = new VBox(15, label, restart);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        dialog.setScene(new Scene(vBox, 400, 150));
        dialog.setTitle("Spielende");
        dialog.show();

        if (gewonnen == true) {
            if (currentDifficulty == 1) {
                user.addEasyWins();
            } else if (currentDifficulty == 0) {
                user.addMediumWins();
            } else if (currentDifficulty == -1) {
                user.addHardWins();
            }

            Users users = new Users();
            users.update(user);

            Label stats = new Label(
                    user.getUsername() + " – Siege:  " +
                            "Einfach: " + user.getEasyWins() + "  " +
                            "Mittel: " + user.getMediumWins() + "  " +
                            "Schwer: " + user.getHardWins()
            );
            stats.setStyle("-fx-font-size: 13px;");

            VBox dialogBox = new VBox(15, label, stats, restart);
            dialogBox.setAlignment(Pos.CENTER);
            dialogBox.setPadding(new Insets(20));

            dialog.setScene(new Scene(dialogBox, 400, 180));
        }
    }

    /**
     * checkt, ob die Eingabe des Benutzers akzeptabel ist und setzt den user für die Runde
     *
     * @param s: Eingabe des Benutzernamens
     * @return: ober die Eingabe aktzeptabel ist
     */

    public boolean manageUser(String s) {
        if (user.checkUser(s)) {
            user = user.selectUser(s);
            return true;
        }
        return false;
    }

    /**
     * Ändert die Farbe einer Eingabebox
     *
     * @param f: Box die geändert werden soll
     * @param c: farbe auf die sie geändert werden soll
     */

    public void changeBoxColor(TextField[] f, char[] c) {
        for (int i = 0; i < f.length; i++) {
            if (c[i] == 'g') {
                f[i].setStyle(f[i].getStyle() + " -fx-background-color: #2E781F;");
            }
            if (c[i] == 'y') {
                f[i].setStyle(f[i].getStyle() + " -fx-background-color: #B59F3B;");
            }
            if (c[i] == 'r') {
                f[i].setStyle(f[i].getStyle() + " -fx-background-color: #962121;");
            }
        }
    }
}

