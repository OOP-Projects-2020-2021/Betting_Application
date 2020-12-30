package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import user.Bet;
import user.User;

import java.io.IOException;
import java.util.ArrayList;

public class SignInController extends SceneController {

    @FXML
    private TextField _username;
    @FXML
    private TextField _password;

    @FXML
    private void SignIn(ActionEvent actionEvent) {
        ArrayList<User> users = sqlConnection.getUsers();

        User current = null;
        for (User u : users) {
            if (u.getUsername().equals(_username.getText())) {
                current = u;
                break;
            }
        }

        if (current == null) {
            System.out.println("The username does not exist!");
        } else if (!current.getPassword().equals(_password.getText())) {
            System.out.println("The combination username-password is incorrect!");
        } else {
            System.out.println("Congratulations! you've just signed in" + current.getName());
            currentUser = current;
            if (!currentUser.isAdmin()) {
                try {
                    changeScene(actionEvent, "/MainMenu.fxml");
                } catch (IOException ioException) {
                    System.out.println("IO exception while trying to changeScene");
                }
            } else {
                try {
                    changeScene(actionEvent, "/AdminMenu.fxml");
                } catch (IOException ioException) {
                    System.out.println("IO exception while trying to changeScene");
                }
            }
        }
    }

    @FXML
    private void goTo(ActionEvent event) throws IOException {
        changeScene(event, "/SignUp.fxml");
    }
}
