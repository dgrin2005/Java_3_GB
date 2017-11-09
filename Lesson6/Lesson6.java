/**
 * Java 3. Lesson 6.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-11-04
 */


package ru.geekbrains.java3.homework;

import java.sql.*;
import java.util.Random;

public class Lesson6 {
    public static void main(String[] args) {
        Task3 t = new Task3();
        t.initDB();
        t.disconnect();
    }
}

class Task1{
    public int[] doTask1(int[] arr) throws RuntimeException {
        int[] res = null;
        int i = arr.length - 1;
        int j = 0;
        boolean found = false;
        while (i >= 0 && !found) {
            found = arr[i] == 4;
            j = i;
            i--;
        }
        if (found) {
            res = new int[arr.length - j - 1];
            for(i = j + 1; i < arr.length; i++) {
                res[i - j - 1] = arr [i];
            }
        } else {
            throw new RuntimeException("Array have no 4");
        }
        return res;
    }
}

class Task2{
    public boolean doTask2(int[] arr) {
        boolean res = true;
        int count1 = 0;
        int count4 = 0;
        int i = 0;
        while (i < arr.length && res) {
            if (arr[i] == 1) {
                res = true;
                count1++;
            } else {
                if (arr[i] == 4) {
                    res = true;
                    count4++;
                } else {
                    res = false;
                }
            }
            i++;
        }
        return res && count1 > 0 && count4 > 0;
    }
}

interface Task3Const {

    final String DRIVERNAME = "org.sqlite.JDBC";
    final String DBNAME = "jdbc:sqlite:main.db";

    final String CREATETABLE = "CREATE TABLE IF NOT EXISTS students (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "    surname TEXT NOT NULL,\n" +
            "    mark DECIMAL(1,0) NOT NULL\n" +
            ");";
    final String CLEARTABLE = "DELETE FROM students;";
    final String FILLTABLE = "INSERT INTO students (surname, mark) VALUES (?, ?);";
    final String ADD = "INSERT INTO students (surname, mark) VALUES (?, ?);";
    final String UPDATE = "UPDATE students SET mark = ? WHERE surname = ?;";
    final String READ = "SELECT * FROM students WHERE mark >= ?;";

}


class Task3 implements Task3Const {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert, psQuery;

    public Task3() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDB() {
        try {
            createTable();
            clearTable();
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void connect() throws Exception {
        Class.forName(DRIVERNAME);
        connection = DriverManager.getConnection(DBNAME);
        stmt = connection.createStatement();
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        stmt.executeUpdate(CREATETABLE);
    }

    private void clearTable() throws SQLException {
        stmt.executeUpdate(CLEARTABLE);
    }

    private void fillTable() throws SQLException {
        connection.setAutoCommit(false);
        psInsert = connection.prepareStatement(FILLTABLE);
        Random rand = new Random();
        for (int i = 1; i <= 1000; i++) {
            psInsert.setString(1, "Student" + i);
            psInsert.setFloat(2, rand.nextInt(5) + 1);
            psInsert.addBatch();
        }
        psInsert.executeBatch();
        connection.setAutoCommit(true);
    }

    public boolean addToTable(String a, int b) throws SQLException {
        psQuery = connection.prepareStatement(ADD);
        psQuery.setString(1, a);
        psQuery.setInt(2, b);
        int rs = psQuery.executeUpdate();
        return rs != 0;
    }

    public boolean updateTable(String a, int b) throws SQLException {
        psQuery = connection.prepareStatement(UPDATE);
        psQuery.setInt(1, b);
        psQuery.setString(2, a);
        int rs = psQuery.executeUpdate();
        return rs != 0;
    }

    public boolean readTable(int a) throws SQLException {
        psQuery = connection.prepareStatement(READ);
        psQuery.setInt(1, a);
        ResultSet rs = psQuery.executeQuery();
        return rs.next();
    }

}
