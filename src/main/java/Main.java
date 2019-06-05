import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Agenda.fxml"));
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
}
