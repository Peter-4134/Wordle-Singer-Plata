import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GUI extends Application{
//--module-path "libs/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("TaschenrechnerSB");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

