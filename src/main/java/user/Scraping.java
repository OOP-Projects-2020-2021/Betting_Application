package user;

import json.JSONDecode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Scraping {
    private ArrayList<League> leagues;

    public void getLeaguesFromJson() {
        this.leagues = new ArrayList<>();
        JSONObject jsonLeagues = null;
        String API_KEY  = "d8af2a63662701e8669fbb12d790c87b";

        String url = "https://api.the-odds-api.com/v3/sports?apiKey=" + API_KEY;
        try
        {
            jsonLeagues = JSONDecode.getJsonFromHttp(url);
        }catch (Exception exception)
        {
            System.out.println("The JSONObject could not be instantiated.. try again");
        }

        assert jsonLeagues != null;
        JSONArray dataLeagues = jsonLeagues.getJSONArray("data");

        for(int iter = 0; iter < dataLeagues.length(); iter++)
        {
            String key = dataLeagues.getJSONObject(iter).getString("key");
            if(key.contains("soccer_epl") || key.contains("soccer_france_ligue_one") || key.contains("soccer_germany_bundesliga") ||
                key.contains("soccer_italy_serie_a") || key.contains("soccer_spain_la_liga"))
            {
                String group = dataLeagues.getJSONObject(iter).getString("group");
                String name = dataLeagues.getJSONObject(iter).getString("details");

                League curr = new League(name, group);

                JSONObject jsonLeagueCurrent = null;

                url = "https://api.the-odds-api.com/v3/odds/?sport=" + key + "&region=uk&apiKey=" + API_KEY;
                try
                {
                    jsonLeagueCurrent = JSONDecode.getJsonFromHttp(url);
                }catch (Exception exception)
                {
                    System.out.println("The JSONObject could not be instantiated.. try again");
                }

                System.out.println(iter);
                assert jsonLeagueCurrent != null;
                JSONArray dataCurrent = jsonLeagueCurrent.getJSONArray("data");
                curr.setGames(dataCurrent);

                this.leagues.add(curr);
            }
        }

    }

    public ArrayList<League> getLeagues()
    {
        return this.leagues;
    }
}