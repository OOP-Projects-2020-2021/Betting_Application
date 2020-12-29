package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        /* Scraping scraping = new Scraping();

        scraping.getLeaguesFromJson();
        ArrayList<League> leagues = scraping.getLeagues();

        for (League l : leagues) {
            for (Match m : l.getGames()) {
                System.out.println(m.getTeam1() + " " + m.getOdd(2).getOdd() + " " + m.getOdd(0).getTeam2() + " " + m.getDate() + " " + m.getTime());
            }
        }*/
       /* JSONArray leagues = jsonObject.getJSONArray("data");
        System.out.println(leagues.length());*/
        //launch(args);
    }
}

