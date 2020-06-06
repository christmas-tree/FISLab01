package com.fis.humans.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";
    private static final String DATABASE = "LabFIS";
    private static final String CONN = "jdbc:sqlserver://localhost:1433;";
    private static final String connectionUrl = CONN
            + "database=" + DATABASE + ";"
            + "user=" + USERNAME + ";"
            + "password=" + PASSWORD + ";";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection;
    }
}
