package org.example.lab_9.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL =
            "jdbc:mysql://localhost:3306/lab9_productos?serverTimezone=America/Lima";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "12345678";

    public static Connection getConnection()
            throws SQLException {

        try {

            Class.forName(
                    "com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD);
    }
}

