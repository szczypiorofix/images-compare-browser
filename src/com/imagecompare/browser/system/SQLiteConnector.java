package com.imagecompare.browser.system;



import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;

public class SQLiteConnector {

    private final String SQL_DRIVER = "org.sqlite.JDBC";
    private final String DATABASE_FOLDER = "db";
    private final String DB_URL = "jdbc:sqlite:";
    private final String SQL_STM = "CREATE TABLE IF NOT EXISTS images (" +
            "id integer PRIMARY KEY," +
            "name text NOT NULL," +
            "filename text NOT NULL," +
            "param1 text NOT NULL," +
            "param2 text NOT NULL," +
            "param3 text NOT NULL," +
            "param4 text NOT NULL" +
            ");";
    private String dbFileName = "";
    private static Connection conn = null;
    private Statement stm = null;

    public SQLiteConnector(String databaseFileName) {
        init(databaseFileName);
    }

    private static Connection getConn(String url) throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(url);
        }
        return conn;
    }

    public void init(String databaseFileName) {
        if (!databaseFileName.equals("")) {
            this.dbFileName = databaseFileName;

            try {

                conn = getConn(DB_URL+this.dbFileName);

                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    Log.put(false, Level.INFO, "Nazwa sterownika bazy danych:" +meta.getDriverName(), this.getClass().getName());
                    Log.put(false, Level.INFO, "Utworzono nową bazę danych.", this.getClass().getName());

                    //System.out.println("The driver name is " + meta.getDriverName());
                    //System.out.println("A new database has been created.");

                    stm = conn.createStatement();
                    stm.execute(SQL_STM);
                    System.out.println("Table created.");
                    Log.put(false, Level.INFO, "Utworzono nową tabelę 'images' w bazie danych.", this.getClass().getName());
                }
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                Log.put(false, Level.WARNING, "Database connection error: " +sqle.getMessage(), this.getClass().getName());
                JOptionPane.showMessageDialog(null, sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
            }
/*            finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }*/
        }
    }

    public static String getStatus() {
        if (conn != null) {
            return "Połaczenie z bazą aktywne";
        }
        return "Brak połączenia z bazą danych!";
    }

    public static Boolean isConnected() {
        return conn != null;
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
