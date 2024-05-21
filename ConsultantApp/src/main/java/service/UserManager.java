package service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import db.ConnectionPool;

public class UserManager implements Serializable {

    private HashMap<String, String> userCredentialsMap;

    public UserManager() {
        userCredentialsMap = new HashMap<>();
        loadUserCredentialsFromDatabase();
    }

    private void loadUserCredentialsFromDatabase() {
        String sqlQuery = "SELECT * FROM user";

        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.prepareStatement(sqlQuery);
            rs = s.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                userCredentialsMap.put(username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (s != null)
                try {
                    s.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
    }

    public boolean loginUser(String username, String password) {
         if (userCredentialsMap.containsKey(username)) {
            String storedPassword = userCredentialsMap.get(username);
            return password.equals(storedPassword);
        } else {
            return false;
        }
    }
}
