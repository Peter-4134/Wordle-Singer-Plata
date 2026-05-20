import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        String word = "PETER"; //zufälliges wort muss ausgewählt werden
        logik = new Logik(word);
        launch(args);
    }

    TextField[][] boxes; // so kann man auf boxen zugreifen
    int counter = 0; // wie viele versuche es gab
    static Logik logik;// zur Überprüfung der Richtigkeit
    User user = new User("null");

    @Override
    public void start(Stage stage) throws Exception {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(15));
        hBox.setSpacing(15);

        Button easy = new Button("einfach");
        Button medium = new Button("mittel");
        Button hard = new Button("schwer");

        hBox.getChildren().addAll(easy,medium,hard);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(15));

        TextField username = new TextField("Benutzername:");
        vBox.getChildren().addAll(username,hBox);
        Scene scene = new Scene(vBox, 250, 150);

        easy.setOnAction(event -> {
            if(manageUser(String.valueOf(username))) game(1, stage);
        });

        medium.setOnAction(event -> {
            if(manageUser(String.valueOf(username))) game(0, stage);
        });

        hard.setOnAction(event -> {
            if(manageUser(String.valueOf(username))) game(-1, stage);
        });


        stage.setTitle("start");
        stage.setScene(scene);
        stage.show();

    }

    public void game(int difficulty, Stage stage){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5.0);
        gridPane.setVgap(5.0);

        int rows = 6+difficulty;
        int cols = 5;

        boxes  = new TextField[rows][5];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField box = new TextField();

                box.setPrefSize(60,60);
                box.setMinSize(60, 60);
                box.setMaxSize(60, 60);

                box.setStyle("-fx-font-size: 24px; " + "-fx-font-weight: bold; " + "-fx-background-radius: 5px; " + "-fx-border-radius: 5px; " + "-fx-border-color: #787c7e; " + "-fx-border-width: 2px;");
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
            char[] c = logik.prüfen(boxes[counter]);
            if(c.length == 1){
                counter --;
            }else{
                changeBoxColor(boxes[counter],c);
            }
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

    public boolean manageUser(String s){
        if(user.checkUser(s)){
            user = user.selectUser(s);
            return true;
        }
        return false;
    }

    public void changeBoxColor(TextField[] f,char[] c){
        for (int i = 0; i < f.length; i++) {
            if(c[i]=='g'){
                f[i].setStyle(f[i].getStyle()+" -fx-background-color: #2E781F;");
            }
            if(c[i]=='y'){
                f[i].setStyle(f[i].getStyle()+" -fx-background-color: #F4FF00;");
            }
            if(c[i]=='r'){
                f[i].setStyle(f[i].getStyle()+" -fx-background-color: #962121;");
            }
        }
    }
}

