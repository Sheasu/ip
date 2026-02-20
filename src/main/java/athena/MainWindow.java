package athena;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Athena athena;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image athenaImage = new Image(this.getClass().getResourceAsStream("/images/DaAthena.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    public void setAthena(Athena a) {
        assert a != null : "Athena engine was not initialized correctly";

        athena = a;
        dialogContainer.getChildren().add(
                DialogBox.getAthenaDialog("The phalanx is formed. Spartan, your orders?", athenaImage)
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        assert input != null : "userInput TextField returned a null value";

        String response = athena.getResponse(input);

        assert response != null : "Athena logic returned a null response";

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAthenaDialog(response, athenaImage)
        );
        userInput.clear();

        boolean isByeCommand = input.trim().equalsIgnoreCase("bye")
                || input.trim().equalsIgnoreCase("b");

        if (!isByeCommand) {
            return;
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }
}
