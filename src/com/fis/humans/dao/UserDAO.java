package com.fis.humans.dao;

import com.fis.humans.connection.DbConnection;
import com.fis.humans.model.User;

import java.sql.*;

public class UserDAO {

    private static UserDAO instance = new UserDAO();

    private UserDAO() {}

    public static UserDAO getInstance() {
        return instance;
    }

    public User authenticate(String username, String password) throws SQLException {
        User user = null;
        Connection con = DbConnection.getConnection();

        String sql = "SELECT Username, Password, Role FROM Users WHERE [Username] = ? AND [Password] = ?";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("Password"));
            user.setRole(rs.getString("Role"));
        }

        rs.close();
        stmt.close();
        con.close();

        return user;
    }
}
