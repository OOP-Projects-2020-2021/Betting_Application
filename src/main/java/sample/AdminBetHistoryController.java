package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import user.Bet;
import user.Odd;

import java.io.IOException;
import java.util.ArrayList;

public class AdminBetHistoryController extends SceneController {

    private final ObservableList<Pane> allBets = FXCollections.observableArrayList();
    private final ArrayList<ObservableList<Pane>> allBetsAsLists = new ArrayList<>();

    @FXML
    private ListView<Pane> listView;

    @FXML
    private ListView<Pane> listViewOdds;

    @FXML
    private void initialize() {
        for (Bet bet : controlledUser.getBets()) {
            Button betButton = new Button(String.valueOf(bet.getTotalOdd()));
            betButton.setLayoutX(10);
            betButton.setLayoutY(12);
            betButton.setOnAction(e -> listViewOdds.setItems(allBetsAsLists.get(controlledUser.getBets().indexOf(bet))));

            Pane pane = new Pane();
            pane.getChildren().add(betButton);
            allBets.add(pane);

            allBetsAsLists.add(constructOsbList(bet));
        }

        if (!allBets.isEmpty())
            listView.setItems(allBets);
        if (!allBetsAsLists.isEmpty())
            listViewOdds.setItems(allBetsAsLists.get(0));

    }

    @FXML
    private ObservableList<Pane> constructOsbList(Bet bet) {
        ObservableList<Pane> currObsList = FXCollections.observableArrayList();
        for (Odd odd : bet.getAllOdds()) {
            Pane auxPane = new Pane();
            Text teams = new Text(odd.getTeam1() + " - " + odd.getTeam2());
            teams.setLayoutY(12);
            teams.setLayoutX(10);

            Label betAndOdd = new Label(odd.getOddType() + " " + odd.getOdd());
            betAndOdd.setLayoutY(12);
            betAndOdd.setLayoutX(200);

            auxPane.getChildren().add(teams);
            auxPane.getChildren().add(betAndOdd);
            currObsList.add(auxPane);
        }

        return currObsList;
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
