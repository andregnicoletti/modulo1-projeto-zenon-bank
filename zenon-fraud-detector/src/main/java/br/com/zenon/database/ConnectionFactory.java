package br.com.zenon.database;

import java.sql.Connection;
import java.sql.DriverManager;

public final class ConnectionFactory {

    private static final String url = "jdbc:mysql://localhost:3306/zenon_frauds";
    private static final String username = "root";
    private static final String password = "senha123";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to database" + e.getMessage(), e);
        } 
    }

}
