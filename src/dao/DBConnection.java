/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;

/**
 * Simple JDBC connection helper.
 * Update URL, USER, PASS to match your MySQL configuration.
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/skillswap_db";
    private static final String USER = "root";
    private static final String PASS = "esha987"; // set your MySQL root password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL Connector/J
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
