import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GUI extends Application {

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

                box.setTextFormatter(new TextFormatter<>(change -> {
                    if (change.getControlNewText().length() <= 1) {
                        change.setText(change.getText().toUpperCase());
                        return change;
                    }
                    return null;
                }));

                gridPane.add(box,j,i);
            }
        }



        StackPane root = new StackPane(gridPane);
        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Wordle");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}

