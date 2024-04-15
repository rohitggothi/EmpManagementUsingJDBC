/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rohit.utility;

/**
 *
 * @author rokie
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connectionPool {

    static Connection conn;

    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(connectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        String url = "jdbc:mysql://localhost:3306/mydb";
        String un = "root";
        String ps = "9340";
        try {
            conn = DriverManager.getConnection(url, un, ps);
        } catch (SQLException ex) {
            Logger.getLogger(connectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;

    }

    public static void main(String[] args) {
        connectDB();
    }
}
