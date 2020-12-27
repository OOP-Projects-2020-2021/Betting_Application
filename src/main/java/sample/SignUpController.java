package sample;

import javafx.fxml.FXML;
import sqlserver.SQLConnection;
import user.User;

import javafx.scene.control.TextField;

import java.util.ArrayList;


public class SignUpController {

    @FXML
    private TextField _username;
    @FXML
    private TextField _password;
    @FXML
    private TextField _CNP;
    @FXML
    private TextField _name;

    @FXML
    private void SignUp() {
        String username, password, CNP, name;

        SQLConnection sqlConnection = new SQLConnection();
        ArrayList<User> users = sqlConnection.getUsers();

        username = _username.getText();
        password = _password.getText();
        CNP = _CNP.getText();
        name = _name.getText();

        boolean usernameCheck = true, passwordCheck = true, CNPCheck = true;

        if (username.length() < 6) {
            usernameCheck = false;
        } else
            for (User u : users) {
                if (username.equals(u.getUsername())) {
                    usernameCheck = false;
                    break;
                }
            }

        if (password.length() < 6) {
            passwordCheck = false;
        }
        if (!CNP.matches("[0-9]+") || CNP.length() != 13) {
            CNPCheck = false;
        }

        if (!usernameCheck) {
            System.out.println("Username is already taken!");
        } else if (!passwordCheck) {
            System.out.println("Password must be at least 6 characters long!");
        } else if (!CNPCheck) {
            System.out.println("Your CNP is not 13 characters long or it must contain only digits!");
        } else {
            User u = new User(username, password, CNP, name, 0.0f, false);
            sqlConnection.insertUser(u);
            System.out.println("YAY");
        }

    }
}
