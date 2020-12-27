package user;

import org.json.JSONArray;

import java.util.ArrayList;

public class League {
    private ArrayList<Match> games;
    private final String name;
    private final String group;

    public League(String name, String group) {
        this.name = name;
        this.group = group;
    }

    /**
     * @param matches - the JSONArray that contains all the matches for the current league
     */
    public void setGames(JSONArray matches) {
        this.games = new ArrayList<>();
        for (int iter = 0; iter < matches.length(); iter++) {
            String team1 = matches.getJSONObject(iter).getJSONArray("teams").getString(0);
            String team2 = matches.getJSONObject(iter).getJSONArray("teams").getString(1);
            String commenceTime = matches.getJSONObject(iter).getString("commence_time");
            JSONArray odds = matches.getJSONObject(iter).getJSONArray("sites").getJSONObject(0).getJSONObject("odds").getJSONArray("h2h");
            ArrayList<Odd> currentOdds = new ArrayList<>();
            for (int i = 0; i < odds.length(); i++) {
                Odd currentOdd = new Odd(Float.parseFloat(odds.get(i).toString()), team1, team2);
                currentOdds.add(currentOdd);
            }
            if (currentOdds.size() < 3) {
                Odd nullOdd = new Odd(0.0f, team1, team2);
            }
            Match curr = new Match(team1, team2, currentOdds, commenceTime);
            this.games.add(curr);
        }
    }

    public ArrayList<Match> getGames() {
        return games;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
}
