package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sqlserver.SQLConnection;
import user.User;

import java.util.ArrayList;

public class SignInController {

    @FXML private TextField _username;
    @FXML private TextField _password;

    @FXML
    private void SignIn() {
        SQLConnection sqlConnection = new SQLConnection();
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
        }
    }
}
