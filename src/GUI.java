import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GUI extends Application {

    TextField[][] boxes = new TextField[6][5];
    int counter = 0;

    @Override
    public void start(Stage stage) throws Exception {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);

        int rows = 6;
        int cols = 5;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField box = new TextField();

                box.setPrefSize(60,60);
                box.setMinSize(60, 60);
                box.setMaxSize(60, 60);

                box.setStyle(
                        "-fx-font-size: 24px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 5px; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-border-color: #787c7e; " +
                                "-fx-border-width: 2px;"
                );
                int finalI = i;

                    box.setTextFormatter(new TextFormatter<>(change -> {
                        int a = finalI;
                    if (a==counter && change.getControlNewText().length() <= 1) {
                        change.setText(change.getText().toUpperCase());
                        return change;
                    }
                    return null;
                }));



                gridPane.add(box,j,i);

                boxes[i][j] = box;
            }
        }

        Button countButton = new Button("Prüfen");
        countButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 15px;");

        countButton.setOnAction(event -> {
            counter++;
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane, countButton);
        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Wordle");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}

