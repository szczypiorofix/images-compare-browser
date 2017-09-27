package com.imagecompare.browser;


import javax.swing.*;
import java.sql.*;

class SQLiteConnector {

    private final String SQL_DRIVER = "org.sqlite.JDBC";
    private final String DATABASE_FOLDER = "db";
    private final String DB_URL = "jdbc:sqlite:db/database.db";
    private Connection conn = null;
    private Statement stm = null;

    SQLiteConnector() {


        try {
            conn = DriverManager.getConnection(DB_URL);



        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
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
