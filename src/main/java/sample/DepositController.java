package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class DepositController extends SceneController{

    @FXML
    private void goBack(ActionEvent actionEvent)
    {
        try
        {
            changeScene(actionEvent, "/MainMenu.fxml");
        }catch (IOException ioException)
        {
            System.out.println("The page could not be loaded! @goBack");
        }
    }
}
