package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sqlserver.SQLConnection;

import java.io.IOException;
import java.sql.*;

public class AdminBettingStatsController extends SceneController {

    @FXML
    private ListView<Pane> listView;

    @FXML
    private void initialize() {
        Connection sqlConnection = null;
        try {
            sqlConnection = DriverManager.getConnection(SQLConnection.getDatabaseUrl(), SQLConnection.getDatabaseUsername(), SQLConnection.getDatabasePassword());

            Statement stmtmDistinctOdds = sqlConnection.createStatement();
            ResultSet distinctOdds = stmtmDistinctOdds.executeQuery("SELECT DISTINCT team1, team2, count(*) as nb FROM Odd GROUP BY team1, team2");

            ObservableList<Pane> allMatchesNbOfBets = FXCollections.observableArrayList();
            while (distinctOdds.next()) {
                Text teams = new Text(distinctOdds.getString("team1") + " - " +
                        distinctOdds.getString("team2"));
                teams.setLayoutY(25);
                teams.setLayoutX(100);
                teams.setStyle(
                        "-fx-font: 24 arial;"
                );

                Text numberOfBets = new Text(distinctOdds.getString("nb"));
                numberOfBets.setLayoutY(25);
                numberOfBets.setLayoutX(900);
                numberOfBets.setStyle(
                        "-fx-font: 24 arial;"
                );

                Pane currentPane = new Pane();
                currentPane.setStyle(
                        "-fx-background-color: cyan;"
                );
                currentPane.getChildren().add(teams);
                currentPane.getChildren().add(numberOfBets);
                allMatchesNbOfBets.add(currentPane);
            }

            listView.setItems(allMatchesNbOfBets);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "/AdminMenu.fxml");
        } catch (IOException ioException) {
            System.out.println("The file could not be changed! @goBackToAdminMenuFromStats");
        }
    }
}
