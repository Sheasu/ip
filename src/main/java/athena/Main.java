package athena;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Athena athena = new Athena();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Athena: Spartan Command");

            stage.setResizable(true);
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);

            fxmlLoader.<MainWindow>getController().setAthena(athena);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}