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
    @FXML
    private Button placeBetButton;

    public void initialize() {

        ArrayList<League> leagues = currentUser.getLeagues();

        constructScene(leagues);

        ///
        placeBetButton = new Button("Place Bet");
        placeBetButton.setLayoutX(632);
        placeBetButton.setLayoutY(820);
        placeBetButton.setPrefWidth(571);
        placeBetButton.setPrefHeight(31);
        placeBetButton.setVisible(false);
        placeBetButton.setOnAction(this::placeBet);
        anchorPane.getChildren().add(placeBetButton);
        anchorPane.getChildren().add(constructBetButton());
        ///

        listView.setItems(games);

    }

    @FXML
    private void placeBet(ActionEvent actionEvent) {
        if (!currentOdds.isEmpty()) {
            Bet currBet = new Bet(currentOdds, 1, currentBetTotalOdd, new java.sql.Date(System.currentTimeMillis()), false);

            currentUser.getBets().add(currBet);
            sqlConnection.insertBet(currBet, currentUser);

            try {
                changeScene(actionEvent, "/MainMenu.fxml");
            } catch (IOException ioException) {
                System.out.println("The file could not be loaded. @placeBet");
            }
        }
    }

    @FXML
    private Button constructBetButton() {
        Button Bet = new Button("Bet");
        Bet.setLayoutX(408);
        Bet.setLayoutY(0);
        Bet.setPrefWidth(220);
        Bet.setPrefHeight(31);
        Bet.setOnAction(actionEvent -> {
            listViewMatches.setItems(currentBetPanes);
            placeBetButton.setVisible(true);
        });

        return Bet;
    }

    @FXML
    public void setBet(Match match, int betIndex, Label betLabel) {
        Odd odd = new Odd(match.getOdd(betIndex).getOdd(), match.getTeam1(), match.getTeam2(), betLabel.getText());
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
        String unpickedColor = "#fc9803";
        String pickedColor = "#1e9e71";

        Pane currentPane = new Pane();
        String teamsString = m.getTeam1() + " - " + m.getTeam2();
        Text teams = new Text(teamsString);
        teams.setLayoutY(25);
        teams.setStyle(
                "-fx-font: 24 arial;"
        );

        Button bet3 = new Button(String.valueOf(m.getOdd(2).getOdd()));
        Button bet1 = new Button(String.valueOf(m.getOdd(0).getOdd()));
        Button bet2 = new Button(String.valueOf(m.getOdd(1).getOdd()));


        Label odd1 = new Label("1");
        odd1.setLayoutY(0);
        odd1.setLayoutX(715);
        Label oddX = new Label("X");
        oddX.setLayoutY(0);
        oddX.setLayoutX(815);
        Label odd2 = new Label("2");
        odd2.setLayoutY(0);
        odd2.setLayoutX(915);


        bet1.setLayoutX(700);
        bet1.setLayoutY(15);

        bet2.setLayoutX(800);
        bet2.setLayoutY(15);

        bet3.setLayoutX(900);
        bet3.setLayoutY(15);

        bet1.setOnAction(e -> {
            bet1.setStyle(
                    "-fx-background-color: #131441;"
            );
        });

        bet1.setStyle(
                "-fx-background-color: " + unpickedColor + ";" +
                        "-fx-background-radius: 0;"
        );
        bet2.setStyle(
                "-fx-background-color: " + unpickedColor + ";" +
                        "-fx-background-radius: 0;"
        );
        bet3.setStyle(
                "-fx-background-color: " + unpickedColor + ";" +
                        "-fx-background-radius: 0;"
        );

        bet1.setOnAction(e -> {
            setBet(m, 0, odd1);
            if (bet1.getStyle().contains(unpickedColor))
                bet1.setStyle(
                        "-fx-background-color: " + pickedColor + ";"
                );
            else
                bet1.setStyle(
                        "-fx-background-color: " + unpickedColor + ";"
                );
            bet2.setStyle(
                    "-fx-background-color: " + unpickedColor + ";"
            );
            bet3.setStyle(
                    "-fx-background-color: " + unpickedColor + ";"
            );
        });
        bet2.setOnAction(e -> {
            setBet(m, 1, odd1);
            if (bet2.getStyle().contains(unpickedColor))
                bet2.setStyle(
                        "-fx-background-color: " + pickedColor + ";"
                );
            else
                bet2.setStyle(
                        "-fx-background-color: " + unpickedColor + ";"
                );
            bet1.setStyle(
                    "-fx-background-color: " + unpickedColor + ";"
            );
            bet3.setStyle(
                    "-fx-background-color: " + unpickedColor + ";"
            );
        });
        bet3.setOnAction(e -> {
            setBet(m, 2, odd1);
            if (bet3.getStyle().contains(unpickedColor))
                bet3.setStyle(
                        "-fx-background-color: " + pickedColor + ";"
                );
            else
                bet3.setStyle(
                        "-fx-background-color: " + unpickedColor + ";"
                );
            bet2.setStyle(
                    "-fx-background-color: " + unpickedColor + ";"
            );
            bet1.setStyle(
                    "-fx-background-color: " + unpickedColor + ";"
            );
        });


        currentPane.setStyle(
                "-fx-background-color: #03fce3;" +
                        "-fx-background-radius: 0;"
        );
        currentPane.setPrefHeight(listView.getFixedCellSize());
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
        button.setOnAction(actionEvent -> {
            listViewMatches.setItems(allMatches.get(leagues.indexOf(l)));
            placeBetButton.setVisible(false);
        });

        return button;
    }

    @FXML
    private void goBack(ActionEvent actionEvent) {
        try {
            changeScene(actionEvent, "/MainMenu.fxml");
        } catch (IOException ioException) {
            System.out.println("The page could not be loaded! @goBack");
        }
    }
}
