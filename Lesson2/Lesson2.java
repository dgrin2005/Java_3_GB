/**
 * Java 3. Lesson 2.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-10-16
 */

package ru.geekbrains.java3.homework;

import java.io.*;
import java.sql.*;

public class Lesson2 implements Lesson2Const{

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert, psQuery;

    public static void main(String[] args) {
        String command;
        boolean cont = true;

        try {
            connect();
            // Task 1
            createTable();
            // Task 2
            clearTable();
            fillTable();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (cont) {
                System.out.println("Введите команду ( /выход - для выхода ):");
                command = reader.readLine();

                String[] com = command.split(" ");
                if (com[0].equals("/цена")) {
                    // Task 3
                    if (com.length == 2) {
                        showCost(com[1]);
                    } else {
                        System.out.println("Команда не опознана");
                    }
                } else {
                    if (com[0].equals("/сменитьцену")) {
                        // Task 4
                        if (com.length == 3) {
                            changeCost(com[1], Float.parseFloat(com[2]));
                        } else {
                            System.out.println("Команда не опознана");
                        }
                    } else {
                        if (com[0].equals("/товарыпоцене")) {
                            // Task 5
                            if (com.length == 3) {
                                showGoods(Float.parseFloat(com[1]), Float.parseFloat(com[2]));
                            } else {
                                System.out.println("Команда не опознана");
                            }
                        } else {
                            if (com[0].equals("/выход")) {
                                cont = false;
                            } else {
                                System.out.println("Команда не опознана");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

    }

    public static void connect() throws Exception {
        Class.forName(DRIVERNAME);
        connection = DriverManager.getConnection(DBNAME);
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable() throws SQLException {
        stmt.executeUpdate(CREATETABLE);
    }

    private static void clearTable() throws SQLException {
        stmt.executeUpdate(CLEARTABLE);
    }

    private static void fillTable() throws SQLException {
        connection.setAutoCommit(false);
        psInsert = connection.prepareStatement(FILLTABLE);
        for (int i = 1; i <= 10000; i++) {
            psInsert.setInt(1, i);
            psInsert.setString(2, "Товар" + i);
            psInsert.setFloat(3, i * 10.f);
            psInsert.addBatch();
        }
        psInsert.executeBatch();
        connection.setAutoCommit(true);
    }

    private static void showCost(String a) throws SQLException {
        psQuery = connection.prepareStatement(SHOWCOST);
        psQuery.setString(1, a);
        ResultSet rs = psQuery.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getFloat(1));
        } else {
            System.out.println("Такого товара нет");
        }
    }

    private static void changeCost(String a, float b) throws SQLException {
        psQuery = connection.prepareStatement(CHANGECOST);
        psQuery.setFloat(1, b);
        psQuery.setString(2, a);
        int rs = psQuery.executeUpdate();
        if (rs != 0) {
            System.out.println("Успех");
        } else {
            System.out.println("Команда не выполнена");
        }
    }

    private static void showGoods(float a, float b) throws SQLException {
        psQuery = connection.prepareStatement(SHOWGOODS);
        psQuery.setFloat(1, a);
        psQuery.setFloat(2, b);
        ResultSet rs = psQuery.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3) + " " + rs.getFloat(4));
        }
    }
}

interface Lesson2Const {

    final String DRIVERNAME = "org.sqlite.JDBC";
    final String DBNAME = "jdbc:sqlite:main.db";

    final String CREATETABLE = "CREATE TABLE IF NOT EXISTS goods (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "    prodid INTEGER NOT NULL UNIQUE,\n" +
            "    title TEXT NOT NULL,\n" +
            "    cost DECIMAL(15,2) NOT NULL\n" +
            ");";
    final String CLEARTABLE = "DELETE FROM goods;";
    final String FILLTABLE = "INSERT INTO goods (prodid, title, cost) VALUES (?, ?, ?);";
    final String SHOWCOST = "SELECT cost FROM goods WHERE title = ?;";
    final String CHANGECOST = "UPDATE goods SET cost = ? WHERE title = ?;";
    final String SHOWGOODS = "SELECT * FROM goods WHERE cost >= ? AND cost <= ?;";

}
