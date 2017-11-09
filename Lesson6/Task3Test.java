/**
 * Java 3. Lesson 6.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-11-02
 */


package ru.geekbrains.java3.homework;

import org.junit.*;

import java.sql.*;

public class Task3Test {
    private Task3 t;

    @Before
    public void init() {
        t = new Task3();
    }

    @Test
    public void testAddToTable () throws SQLException {
        Connection conn = t.getConnection();
        Savepoint sp1 = conn.setSavepoint();
        Assert.assertEquals(true, t.addToTable("Ivanov", 4));
        conn.rollback(sp1);
    }

    @Test
    public void test1UpdateTable () throws SQLException {
        Connection conn = t.getConnection();
        Savepoint sp1 = conn.setSavepoint();
        Assert.assertEquals(true, t.updateTable("Student20", 5));
        conn.rollback(sp1);
    }

    @Test
    public void test2UpdateTable () throws SQLException {
        Connection conn = t.getConnection();
        Savepoint sp1 = conn.setSavepoint();
        Assert.assertEquals(true, t.updateTable("Ivanov", 5));
        conn.rollback(sp1);
    }

    @Test
    public void test1ReadTable () throws SQLException {
        Assert.assertEquals(true, t.readTable(3));
    }

    @Test
    public void test2ReadTable () throws SQLException {
        Assert.assertEquals(true, t.readTable(6));
    }

    @After
    public void end() {
        t.disconnect();
    }

}
