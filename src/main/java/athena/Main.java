package athena;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the JavaFX application.
 * Sets up the primary stage and initializes the GUI components.
 */
public class Main extends Application {
    private final Athena athena = new Athena();

    /**
     * Starts the JavaFX application lifecycle.
     *
     * @param stage The primary stage for this application.
     */
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
