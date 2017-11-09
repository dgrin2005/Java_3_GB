/**
 * Java 3. Lesson 6.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-11-04
 */


package ru.geekbrains.java3.homework;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.Parameterized;
import java.util.*;

@RunWith(Parameterized.class)
public class Task2Test {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true, new int[]{2, 1, 4 , 4, 1, 1}},
                {true, new int[]{4, 4, 4 , 4, 4}},
                {true, new int[]{1, 1}},
                {true, new int[]{1, 1, 4 , 4, 4, 4, 4, 1, 1}},
        });
    }
    private Task2 t;
    private boolean a;
    private int[] b;

    public Task2Test(boolean a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Before
    public void init() {
        t = new Task2();
    }

    @Test
    public void testTask2 () {
        Assert.assertEquals(a, t.doTask2(b));
    }

}
