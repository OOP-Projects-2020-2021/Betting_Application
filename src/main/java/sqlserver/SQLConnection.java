package sqlserver;

import user.User;

import java.sql.*;
import java.util.ArrayList;

public class SQLConnection {
    private ArrayList<User> users;

    public SQLConnection()
    {
        this.setUsers();
    }

    public void insertUser(User u) {

        String DATABASE_URL = "jdbc:sqlserver://localhost:1433;databaseName=BettingAppDB;";
        String DATABASE_USERNAME = "alex";
        String DATABASE_PASSWORD = "1234567";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            PreparedStatement stmt = conn.prepareStatement("insert into UserX values (?, ?, ?, ?, ?, ?, 0)");
            stmt.setInt(1, this.users.size() + 1);
            stmt.setString(2, u.getUsername());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getName());
            stmt.setString(5, u.getCNP());
            stmt.setFloat(6, u.getBalance());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void setUsers(){
        ArrayList<User> users = new ArrayList<>();

        String DATABASE_URL = "jdbc:sqlserver://localhost:1433;databaseName=BettingAppDB;";
        String DATABASE_USERNAME = "alex";
        String DATABASE_PASSWORD = "1234567";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            assert conn != null;
            Statement stm = conn.createStatement();
            ResultSet rs;

            rs = stm.executeQuery("SELECT username, password, CNP, name, balance, admin FROM UserX");

            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("CNP"), rs.getString("name"), rs.getFloat("balance"),
                        rs.getBoolean("admin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
