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
public class Task1Test {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 7}, new int[]{1, 2, 4 , 4, 2, 3, 4, 1, 7}},
                {new int[]{1, 7}, new int[]{1, 2, 0 , 0, 2, 3, 0, 1, 7}},
                {new int[]{1, 7}, new int[]{1, 2, 4 , 4, 2, 3, 0, 1, 7}},
                {new int[]{0, 2, 3, 0, 1, 7}, new int[]{1, 2, 4 , 0, 2, 3, 0, 1, 7}},
        });
    }
    private Task1 t;
    private int[] a;
    private int[] b;

    public Task1Test(int[] a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Before
    public void init() {
        t  = new Task1();
    }

    @Test
    public void testTask1 () {
        Assert.assertTrue("Arrays are not equal", java.util.Arrays.equals(a, t.doTask1(b)));
    }

    @Test(expected = RuntimeException.class)
    public void testExceptionTask1 () {
        t.doTask1(b);
    }

}
