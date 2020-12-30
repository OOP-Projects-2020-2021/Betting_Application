package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminPersonalDataController extends SceneController {

    @FXML
    private Label CNP;

    @FXML
    private void initialize() {
        this.CNP.setText(controlledUser.getCNP());
    }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "/AdminMenu.fxml");
        } catch (IOException ioException) {
            System.out.println("The page could not be loaded! @goBack");
        }
    }
}
