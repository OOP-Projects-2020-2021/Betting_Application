package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class MainMenuController extends SceneController {

    @FXML
    private Button BetMenu;
    @FXML
    private Button BetHistory;
    @FXML
    private Button Deposit;

    @FXML
    private void goToBetMenu(ActionEvent event) {
        try {
            changeScene(event, "/BetMenu.fxml");
        } catch (IOException ioException) {
            System.out.println("The scene could not be changed! @goToMenu");
        }
    }

    @FXML
    private void goToBetHistory(ActionEvent event) {
        try {
            changeScene(event, "/BetHistory.fxml");
        } catch (IOException ioException) {
            System.out.println("The scene could not be changed! @goToBetHistory");
        }
    }

    @FXML
    private void goToDeposit(ActionEvent event) {
        try {
            changeScene(event, "/Deposit.fxml");
        } catch (IOException ioException) {
            System.out.println("The scene could not be changed! @goToDeposit");
        }
    }
}
