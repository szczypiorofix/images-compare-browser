package com.imagecompare.browser;


import com.imagecompare.browser.system.Log;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;

class SQLiteConnector {

    private final String SQL_DRIVER = "org.sqlite.JDBC";
    private final String DATABASE_FOLDER = "db";
    private final String DB_URL = "jdbc:sqlite:";
    private String dbFileName = "";
    private Connection conn = null;
    private Statement stm = null;

    SQLiteConnector(String databaseFileName) {

        this.dbFileName = databaseFileName;
        //System.out.println(DB_URL+this.dbFileName);

        try {
            conn = DriverManager.getConnection(DB_URL+this.dbFileName);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                Log.put(false, Level.INFO, "Nazwa sterownika bazy danych:" +meta.getDriverName(), this.getClass().getName());
                Log.put(false, Level.INFO, "Utworzono nową bazę danych.", this.getClass().getName());
                //System.out.println("The driver name is " + meta.getDriverName());
                //System.out.println("A new database has been created.");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            Log.put(false, Level.WARNING, "Database connection error: " +sqle.getMessage(), this.getClass().getName());
            JOptionPane.showMessageDialog(null, sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }




    }
}
