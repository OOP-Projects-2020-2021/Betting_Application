package sample;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import user.League;
import user.Match;
import user.Scraping;

import java.awt.*;
import java.util.ArrayList;

public class SampleController {

    @FXML private VBox vbox;
    @FXML private TilePane epl;
    @FXML private ScrollPane scroll;

    public void initialize()
    {
        Scraping scraping = new Scraping();
        scraping.getLeaguesFromJson();
        ArrayList<League> leagues = scraping.getLeagues();

        for(League l : leagues)
        {
            vbox.getChildren().add(new Text(l.getName()));
            for(Match m : l.getGames())
            {
                vbox.getChildren().add(new Text(m.getTeam1()));
                vbox.getChildren().add(new Text(m.getTeam2()));
                vbox.getChildren().add(new Text(m.getDate()));
                vbox.getChildren().add(new Text(m.getTime()));
                System.out.println(m.getTeam1() + " " + m.getTeam2() + " " + m.getDate() + " " + m.getTeam2());
            }
        }
    }
}
