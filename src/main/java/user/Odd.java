package user;

public class Odd {
    private final float odd;
    private final String Team1;
    private final String Team2;

    public Odd(Float odd, String t1, String t2) {
        this.odd = odd;
        this.Team1 = t1;
        this.Team2 = t2;
    }

    public float getOdd() {
        return odd;
    }

    public String getTeam1() {
        return Team1;
    }

    public String getTeam2() {
        return Team2;
    }
}
