/**
 * Java 3. Lesson 7.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-11-05
 */


package ru.geekbrains.java3.homework;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
@interface BeforeSuite {
}

@Retention(RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
@interface Test {
    int priority() default 5;
}

@Retention(RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
@interface AfterSuite {
}

public class Lesson7 {
    static final int MAXPRIORITY = 10;

    public static void main(String[] args) {
        try {
            System.out.println("Class");
            start(Test1.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
        try {
            System.out.println("Class name");
            start("ru.geekbrains.java3.homework.Test1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Class cl) throws Exception {
        Method[] methods = cl.getDeclaredMethods();
        int countBS = 0;
        int countAS = 0;
        Method methodBS = null;
        Method methodAS = null;
        ArrayList<Method> metodsTest = new ArrayList<>();
        ArrayList<Integer> metodsPriority = new ArrayList<>();
        for (Method m : methods) {
            if (m.getAnnotation(BeforeSuite.class) != null) {
                countBS++;
                methodBS = m;
            }
            if (m.getAnnotation(AfterSuite.class) != null) {
                countAS++;
                methodAS = m;
            }
            if (m.getAnnotation(Test.class) != null) {
                int p = m.getAnnotation(Test.class).priority();
                if (p < 1 || p > MAXPRIORITY) {
                    throw new RuntimeException("Priority out of range");
                } else {
                    metodsTest.add(m);
                    metodsPriority.add(p);
                }
            }
        }
        if (countBS > 1) {
            throw new RuntimeException("Too many @BeforeSuite");
        }
        if (countAS > 1) {
            throw new RuntimeException("Too many @AfterSuite");
        }
        Object obj = null;
        obj = cl.newInstance();
        if (countBS == 1) {
            methodBS.invoke(obj);
        }
        for (int p = MAXPRIORITY; p >= 1; p--) {
            for (int i = 0; i < metodsPriority.size(); i++) {
                if (metodsPriority.get(i) == p) {
                    metodsTest.get(i).invoke(obj);
                }
            }
        }
        if (countAS == 1) {
            methodAS.invoke(obj);
        }
    }

    public static void start(String className) throws Exception {
        Class cl = Class.forName(className);
        start(cl);
    }
}

class Test1 {

    @BeforeSuite
    void doBeforeTest1() {
        System.out.println("Before Test1");
    }

    @Test(priority = 1)
    void doOperation1Test1() {
        System.out.println("Operation 1 Test1");
    }

    @Test(priority = 10)
    void doOperation2Test1() {
        System.out.println("Operation 2 Test1");
    }

    @Test
    void doOperation3Test1() {
        System.out.println("Operation 3 Test1");
    }

    @AfterSuite
    void doAfterTest1() {
        System.out.println("After Test1");
    }
}
