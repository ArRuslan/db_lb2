package me.ruslan.dblb1.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    public static final String URL = "jdbc:mysql://localhost:3306/nure_db_lb1";
    public static final String USER = "nure";
    public static final String PASSWORD = "123456";

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
