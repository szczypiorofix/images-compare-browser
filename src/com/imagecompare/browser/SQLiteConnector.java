package com.imagecompare.browser;

import java.sql.*;

class SQLiteConnector {

    private final String SQL_DRIVER = "org.sqlite.JDBC";
    private final String DB_URL = "jdbc:sqlite:res/database.db";
    private Connection conn = null;
    private Statement stm = null;

    SQLiteConnector() {

        try {
            Class.forName(SQL_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stm = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia.");
            e.printStackTrace();
        }

        String createCzytelnicy = "CREATE TABLE IF NOT EXISTS czytelnicy (id_czytelnika INTEGER PRIMARY KEY AUTOINCREMENT, imie varchar(255), nazwisko varchar(255), pesel int)";
        String createKsiazki = "CREATE TABLE IF NOT EXISTS ksiazki (id_ksiazki INTEGER PRIMARY KEY AUTOINCREMENT, tytul varchar(255), autor varchar(255))";
        String createWypozyczenia = "CREATE TABLE IF NOT EXISTS wypozyczenia (id_wypozycz INTEGER PRIMARY KEY AUTOINCREMENT, id_czytelnika int, id_ksiazki int)";

        String checkIfTableExists = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name='table_name';";


        try {
            stm.execute("CREATE TABLE IF NOT EXISTS images (id integer PRIMARY KEY, name TEXT NOT NULL);");
            //stm.execute("INSERT INTO images (id, name) VALUES (0, dupa)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO images (id, name) VALUES(?,?)";

        try (
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "Dupa");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ResultSet result = stm.executeQuery("SELECT * FROM images;");
            int count = 0;
            while (result.next()){
                count = result.getInt("id");
            }
            System.out.println("Ilość tablic: " +count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
