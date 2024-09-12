package com.coder.judge.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static Connection _connection = null;

    private DB() {
    }

    public static Connection connection() throws SQLException {
        if (_connection == null || _connection.isClosed()) {
            try {
                var jdbcUrl = DatabaseConfig.getDbUrl();
                var user = DatabaseConfig.getDbUsername();
                var password = DatabaseConfig.getDbPassword();
                _connection = DriverManager.getConnection(jdbcUrl, user, password);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw e;
            }
        }
        return _connection;
    }
}
