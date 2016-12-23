package com.example;

import java.sql.*;

/**
 * Created by omatikaya on 19/12/2016.
 */
public class MysqlJdbcTemplate {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/shop?useSSL=false";
    private static final String user = "admin";
    private static final String password = "X@ker1973!";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;

    public MysqlJdbcTemplate() {
        init();
    }

    private void init() {
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query) throws SQLException {
        return stmt.executeQuery(query);
    }

    public void update(String query) throws SQLException {
        stmt.executeUpdate(query);
    }

    public void insert(String query) throws SQLException {
        stmt.execute(query);
    }
}
