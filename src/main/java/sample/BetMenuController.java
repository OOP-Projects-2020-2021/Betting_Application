package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import user.*;

import java.io.IOException;
import java.util.ArrayList;

public class BetMenuController extends SceneController {

    private final ObservableList<Button> games = FXCollections.observableArrayList();
    private final ArrayList<ObservableList<Pane>> allMatches = new ArrayList<>();
    private final ArrayList<Odd> currentOdds = new ArrayList<>();
    private final ObservableList<Pane> currentBetPanes = FXCollections.observableArrayList();
    private float currentBetTotalOdd = 1.0f;

    @FXML
    private ListView<Button> listView;
    @FXML
    private ListView<Pane> listViewMatches;
    @FXML
    private AnchorPane anchorPane;

    public void initialize() {
        currentUser = new User("a", "a", "a", "a", 0.0f, true, scraping.getLeagues(), new ArrayList<>(), 1);

        ArrayList<League> leagues = currentUser.getLeagues();

        constructScene(leagues);

        ///
        Button empty = new Button();
        empty.setVisible(false);
        games.add(empty);
        games.add(empty);
        games.add(empty);
        games.add(empty);
        games.add(empty);
        games.add(empty);
        ///

        ///
        Button placeBetButton = new Button("Place Bet");
        placeBetButton.setLayoutX(700);
        placeBetButton.setLayoutY(400);
        placeBetButton.setVisible(false);
        placeBetButton.setOnAction(e -> placeBet());
        anchorPane.getChildren().add(placeBetButton);
        ///

        games.add(constructBetButton(placeBetButton));
        listView.setItems(games);

    }

    @FXML
    private void placeBet() {
        if (!currentOdds.isEmpty()) {
            Bet currBet = new Bet(currentOdds, 1, currentBetTotalOdd, new java.sql.Date(System.currentTimeMillis()), false);

            currentUser.getBets().add(currBet);
            sqlConnection.insertBet(currBet, currentUser);
        }
    }

    @FXML
    private Button constructBetButton(Button placeBetButton) {
        Button Bet = new Button("Bet");
        Bet.setOnAction(actionEvent -> {
            listViewMatches.setItems(currentBetPanes);
            placeBetButton.setVisible(!placeBetButton.isVisible());
        });

        return Bet;
    }

    @FXML
    public void setBet(Match match, int betIndex, Label betLabel) {
        Odd odd = new Odd(match.getOdd(betIndex).getOdd(), match.getTeam1(), match.getTeam2());
        Odd oddToRemove = null;

        for (Odd o : currentOdds) {
            if ((o.getTeam2().equals(odd.getTeam2()) && o.getTeam1().equals(odd.getTeam1()) &&
                    o.getOdd() == odd.getOdd())) {

                //remove the pane
                currentBetPanes.remove(currentOdds.indexOf(o));

                //remove the oddValue
                currentBetTotalOdd /= o.getOdd();

                //remove the odd !!
                currentOdds.remove(o);

                //returning
                return;

            } else if (o.getTeam1().equals(odd.getTeam1()) && o.getTeam2().equals(odd.getTeam2())) {
                //remove the pane
                currentBetPanes.remove(currentOdds.indexOf(o));

                //remove the oddValue
                currentBetTotalOdd /= o.getOdd();

                //remove the odd !!
                oddToRemove = o;

                //not returning
            }
        }

        if (oddToRemove != null)
            currentOdds.remove(oddToRemove);

        Pane pane = new Pane();
        Text teams = new Text(odd.getTeam1() + " - " + odd.getTeam2());
        teams.setLayoutY(18);
        teams.setLayoutX(0);

        Label betAndOdd = new Label(
                betLabel.getText() + " : " + odd.getOdd());
        betAndOdd.setLayoutY(12);
        betAndOdd.setLayoutX(200);

        pane.getChildren().add(teams);
        pane.getChildren().add(betAndOdd);

        currentBetPanes.add(pane);
        currentOdds.add(odd);
        currentBetTotalOdd *= odd.getOdd();
        System.out.println(currentBetTotalOdd);
    }

    @FXML
    private void constructScene(ArrayList<League> leagues) {
        for (League l : leagues) {
            games.add(constructButton(leagues, l));

            ListView<Pane> currentMatches = new ListView<>();

            ObservableList<Pane> matches = FXCollections.observableArrayList();
            for (Match m : l.getGames()) {
                matches.add(constructPane(m));
            }

            currentMatches.setItems(matches);
            allMatches.add(matches);
        }
    }

    @FXML
    private Pane constructPane(Match m) {
        Pane currentPane = new Pane();
        String teamsString = m.getTeam1() + " - " + m.getTeam2();
        Text teams = new Text(teamsString);
        teams.setLayoutY(25);
        teams.maxWidth(100);
        Label odd1 = new Label("1");
        odd1.setLayoutY(0);
        odd1.setLayoutX(515);
        Button bet1 = new Button(String.valueOf(m.getOdd(0).getOdd()));
        bet1.setOnAction(e -> setBet(m, 0, odd1));
        bet1.setLayoutX(500);
        bet1.setLayoutY(18);
        /*bet1.setStyle(
                "-fx-background-radius: 2.5em; "
        );*/


        Label oddX = new Label("X");
        oddX.setLayoutY(0);
        oddX.setLayoutX(615);
        Button bet2 = new Button(String.valueOf(m.getOdd(1).getOdd()));
        bet2.setOnAction(e -> setBet(m, 1, oddX));
        bet2.setLayoutX(600);
        bet2.setLayoutY(18);

        Label odd2 = new Label("2");
        odd2.setLayoutY(0);
        odd2.setLayoutX(715);
        Button bet3 = new Button(String.valueOf(m.getOdd(2).getOdd()));
        bet3.setOnAction(e -> setBet(m, 2, odd2));
        bet3.setLayoutX(700);
        bet3.setLayoutY(18);

        currentPane.getChildren().add(teams);
        currentPane.getChildren().add(bet1);
        currentPane.getChildren().add(bet2);
        currentPane.getChildren().add(bet3);
        currentPane.getChildren().add(odd1);
        currentPane.getChildren().add(oddX);
        currentPane.getChildren().add(odd2);

        return currentPane;
    }

    @FXML
    private Button constructButton(ArrayList<League> leagues, League l) {
        Button button = new Button(l.getName());
        button.setPrefWidth(320);
        button.setOnAction(actionEvent -> listViewMatches.setItems(allMatches.get(leagues.indexOf(l))));

        return button;
    }

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
