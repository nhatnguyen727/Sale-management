/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TechCare
 */
public class connectDB {

    public static Connection connectSQLServer() {
        Connection connection = null;
        try {
            String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(JDBC_DRIVER);
            String DB_URL = "jdbc:sqlserver://localhost;";
            String DATABASENAME = "databaseName=doansem2;";
            String USER = "user=sa;";
            String PASS = "password=12345";
            connection = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(connectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
