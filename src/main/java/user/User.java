package user;

public class User {
    private String username;
    private String password;
    private final String CNP;
    private String name;
    private float balance;
    private boolean admin;

    public User(String username, String password, String CNP, String name, float balance, boolean admin) {
        this.username = username;
        this.password = password;
        this.CNP = CNP;
        this.name = name;
        this.balance = balance;
        this.admin = admin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCNP() {
        return CNP;
    }

    public String getName() {
        return name;
    }

    public float getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return admin;
    }
}
