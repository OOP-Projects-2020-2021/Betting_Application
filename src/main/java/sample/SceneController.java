package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import sqlserver.SQLConnection;
import user.Scraping;
import user.User;

import java.io.IOException;

public class SceneController {

    public static Scraping scraping = null;
    public static SQLConnection sqlConnection = null;
    public static User currentUser = null;
    public static ActionEvent actionEvent;

    public SceneController() {
        if(scraping == null)
        {
            scraping = new Scraping();
            scraping.getLeaguesFromJson();
            sqlConnection = new SQLConnection();
        }
    }

    public void changeScene(ActionEvent actionEvent, String fxmlFilePath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlFilePath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
