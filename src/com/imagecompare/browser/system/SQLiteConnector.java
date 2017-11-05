package com.imagecompare.browser.system;



import com.imagecompare.browser.model.ImageItem;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class SQLiteConnector {


    private final static String DB_URL = "jdbc:sqlite:";
    private final static String SQL_CR_TB_IMAGES = "CREATE TABLE IF NOT EXISTS images (" +
            "image_id integer PRIMARY KEY AUTOINCREMENT," +
            "image_name text NOT NULL," +
            "image_filepath text NOT NULL," +
            "param1 text NOT NULL," +
            "param2 text NOT NULL," +
            "param3 text NOT NULL," +
            "param4 text NOT NULL," +
            "param5 text NOT NULL" +
            ");";
/*    private final static String SQL_CR_TB_PARAMNAMES = "CREATE TABLE IF NOT EXISTS paramnames (" +
            "param_id integer PRIMARY KEY AUTOINCREMENT," +
            "param_name text NOT NULL" +
            ");";
    private final static String SQL_CR_TB_IMAGEPARAMS = "CREATE TABLE IF NOT EXISTS imageparams (" +
            "param_id integer NOT NULL," +
            "param_value text NOT NULL," +
            "FOREIGN KEY (param_id) REFERENCES images(image_id)" +
            ");";*/
    private final static String SQL_GET_ITEMS = "SELECT * FROM images;";
    private static String dbFileName = "";
    private static Connection conn = null;
    private static Statement stm = null;

    private SQLiteConnector(String databaseFileName) {
        //init(databaseFileName);
    }

    private static Connection getConn(String url) throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(url);
            Log.put(false, Level.INFO, "Utworzono nowe połączenie z bazą danych.", SQLiteConnector.class.getName());
        }
        return conn;
    }


    public static Boolean createNewDatabaseFile(String databaseFileName) {
        boolean success = false;
        if (!databaseFileName.equals("")) {
            dbFileName = databaseFileName;

            try {
                conn = getConn(DB_URL+dbFileName);

                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    Log.put(false, Level.INFO, "Nazwa sterownika bazy danych:" +meta.getDriverName(), SQLiteConnector.class.getName());
                    Log.put(false, Level.INFO, "Utworzono nową bazę danych.", SQLiteConnector.class.getName());

                    stm = conn.createStatement();

                    // Nowa tabela 'images'
                    stm.execute(SQL_CR_TB_IMAGES);
                    Log.put(false, Level.INFO, "Utworzono nową tabelę 'images' w bazie danych.", SQLiteConnector.class.getName());

/*                    // Nowa tabela 'paramnames'
                    stm.execute(SQL_CR_TB_PARAMNAMES);
                    Log.put(false, Level.INFO, "Utworzono nową tabelę 'paramnames' w bazie danych.", SQLiteConnector.class.getName());

                    // Nowa tabela 'imageparams'
                    stm.execute(SQL_CR_TB_IMAGEPARAMS);
                    Log.put(false, Level.INFO, "Utworzono nową tabelę 'imageparams' w bazie danych.", SQLiteConnector.class.getName());*/

                    success = true;
                }
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                Log.put(false, Level.WARNING, "Database connection error: " +sqle.getMessage(), SQLiteConnector.class.getName());
                JOptionPane.showMessageDialog(null, sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return success;
    }

    public static Boolean connectToDatabase(String databaseFileName) {
        if (!databaseFileName.equals("")) {
            dbFileName = databaseFileName;
            try {
                conn = getConn(DB_URL+dbFileName);
                return true;
            } catch (SQLException sqle) {
                Log.put(false, Level.WARNING, "Database connection error: " +sqle.getMessage(), SQLiteConnector.class.getName());
                JOptionPane.showMessageDialog(null, sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public static ArrayList<ImageItem> getResults() {
        ArrayList<ImageItem> imageItems = null;
        try {
            conn = getConn(DB_URL+dbFileName);
            if (conn != null) {
                stm = conn.createStatement();
                ResultSet resultSet = stm.executeQuery(SQL_GET_ITEMS);

                imageItems = new ArrayList<>(resultSet.getFetchSize());
                int resultsCounter = 0;

                while (resultSet.next()) {
                    imageItems.add(new ImageItem(resultSet.getInt("image_id"),
                            resultSet.getString("image_name"),
                            resultSet.getString("image_filepath"),
                            resultSet.getString("param1"),
                            resultSet.getString("param2"),
                            resultSet.getString("param3"),
                            resultSet.getString("param4"),
                            resultSet.getString("param5")));
                    resultsCounter++;
                }
                resultSet.close();

                Log.put(false, Level.INFO, "Zwróconych wyników z bazy danych: " +resultsCounter, SQLiteConnector.class.getName());
                Log.put(false, Level.INFO, "Odczyt z bazy danych "+ dbFileName +" prawidłowy.", SQLiteConnector.class.getName());
            }
        } catch(SQLException sqle) {
            Log.put(false, Level.WARNING, "Błąd połączenia z bazą danych: " +sqle.getMessage(), SQLiteConnector.class.getName());
            JOptionPane.showMessageDialog(null, "Błąd: " +sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
        }
        return imageItems;
    }

    public static void updateItemInDatabase(ImageItem imageItem) {
        PreparedStatement preparedStatement = null;
        try {
            conn = getConn(DB_URL+dbFileName);
            String sqlQuery = "UPDATE images SET " +
                    "image_name = ?, " +
                    "param1 = ?, " +
                    "param2 = ?, " +
                    "param3 = ?, " +
                    "param4 = ?, " +
                    "param5 = ? " +
                    "WHERE image_id = ?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, imageItem.getName());
            preparedStatement.setString(2, imageItem.getParam1());
            preparedStatement.setString(3, imageItem.getParam2());
            preparedStatement.setString(4, imageItem.getParam3());
            preparedStatement.setString(5, imageItem.getParam4());
            preparedStatement.setString(6, imageItem.getParam5());
            preparedStatement.setInt(7, imageItem.getId());

            preparedStatement.executeUpdate();
            //int r = preparedStatement.executeUpdate();
            //System.out.println("Result: " +r);
        } catch (SQLException sqle) {
            Log.put(false, Level.WARNING, "Błąd połączenia z bazą danych: " +sqle.getMessage(), SQLiteConnector.class.getName());
            JOptionPane.showMessageDialog(null, "Błąd: " +sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
        }
        finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeItemFromDatabase(ImageItem imageItem) {
        try {
            conn = getConn(DB_URL+dbFileName);
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM images WHERE image_id = ?;");
            preparedStatement.setInt(1, imageItem.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException sqle) {
            Log.put(false, Level.WARNING, "Błąd połączenia z bazą danych: " +sqle.getMessage(), SQLiteConnector.class.getName());
            JOptionPane.showMessageDialog(null, "Błąd: " +sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void insertItemToDatabase(ImageItem imageItem) {
        try {
            conn = getConn(DB_URL+dbFileName);
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO images(image_name, image_filepath, param1, param2, param3, param4, param5) VALUES(?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, imageItem.getName());
            preparedStatement.setString(2, imageItem.getFilename());
            preparedStatement.setString(3, imageItem.getParam1());
            preparedStatement.setString(4, imageItem.getParam2());
            preparedStatement.setString(5, imageItem.getParam3());
            preparedStatement.setString(6, imageItem.getParam4());
            preparedStatement.setString(7, imageItem.getParam5());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.put(false, Level.INFO, "Wstawiono nowe dane do bazy danych.", SQLiteConnector.class.getName());
        } catch (SQLException sqle) {
            Log.put(false, Level.WARNING, "Błąd połączenia z bazą danych: " +sqle.getMessage(), SQLiteConnector.class.getName());
            JOptionPane.showMessageDialog(null, "Błąd: " +sqle.getMessage(), "Uwaga !!!", JOptionPane.ERROR_MESSAGE);
        }
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
            Log.put(false, Level.WARNING, "Błąd poczas zamykania połączenia z bazą danych !!! : " +e.getMessage(), SQLiteConnector.class.getName());
        }
    }
}
