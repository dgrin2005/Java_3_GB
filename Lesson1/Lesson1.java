/**
 * Java 3. Lesson 1.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-10-12
 */

package ru.geekbrains.java3.homework;

import java.util.ArrayList;
import java.util.Arrays;

public class Lesson1 {

    public static void main(String[] args) {


        // Task 1
        System.out.println("Task1");
        String[] ars = {"1-2-3", "123", "123.0f", "-123.0"};
        System.out.println(Arrays.toString(ars));
        System.out.println("swap elementes 0 and 3");
        swap(ars, 0, 3);
        System.out.println(Arrays.toString(ars));
        System.out.println();

        Integer[] ari = {1, 12, 123, -123};
        System.out.println(Arrays.toString(ari));
        System.out.println("swap elementes 0 and 3");
        swap(ari, 0, 3);
        System.out.println(Arrays.toString(ari));
        System.out.println();

        Double[] ard = {1., 12., 123., -123.};
        System.out.println(Arrays.toString(ard));
        System.out.println("swap elementes 0 and 3");
        swap(ard, 0, 3);
        System.out.println(Arrays.toString(ard));
        System.out.println();

        Fruit[] arf = {new Orange(), new Apple(), new Orange()};
        System.out.println(Arrays.toString(arf));
        System.out.println("swap elementes 0 and 1");
        swap(arf, 0, 1);
        System.out.println(Arrays.toString(arf));
        System.out.println();

        System.out.println();

        // Task 2
        System.out.println("Task2");
        String[] arrString = {"1-2-3", "123", "123.0f", "-123.0"};
        ArrayList<String> arlString = convertArrayToArrayList(arrString);
        System.out.println(Arrays.toString(arlString.toArray()));

        Integer[] arrInteger = {1, 12, 123, -123};
        ArrayList<Integer> arlInteger = convertArrayToArrayList(arrInteger);
        System.out.println(Arrays.toString(arlInteger.toArray()));

        Double[] arrDouble = {1., 12., 123., -123.};
        ArrayList<Double> arlDouble = convertArrayToArrayList(arrDouble);
        System.out.println(Arrays.toString(arlDouble.toArray()));

        Fruit[] arrFruit = {new Orange(), new Apple(), new Orange()};
        ArrayList<Fruit> arlFruit = convertArrayToArrayList(arrFruit);
        System.out.println(Arrays.toString(arlFruit.toArray()));
        System.out.println("\n");

        // Task 3
        System.out.println("Task3");
        Box<Orange> bo1 = new Box(new ArrayList<>());
        Box<Orange> bo2 = new Box(new ArrayList<>());
        Box<Apple> ba1 = new Box(new ArrayList<>());
        Box<Apple> ba2 = new Box(new ArrayList<>());

        bo1.addFruit(new Orange());
        bo1.addFruit(new Orange());
        ba1.addFruit(new Apple());
        ba1.addFruit(new Apple());
        ba1.addFruit(new Apple());

        System.out.println("bo1 = " + bo1.printBox());
        System.out.println("weight bo1 = " + bo1.getWeight());
        System.out.println("bo2 = " + bo2.printBox());
        System.out.println("weight bo2 = " + bo2.getWeight());
        System.out.println("ba1 = " + ba1.printBox());
        System.out.println("weight ba1 = " + ba1.getWeight());
        System.out.println("ba2 = " + ba2.printBox());
        System.out.println("weight ba2 = " + ba2.getWeight());
        System.out.println("compare bo1 and ba1 : " + bo1.compare(ba1));

        System.out.println("moving bo1 to bo2");
        bo1.moveFruits(bo2);

        System.out.println("bo1 = " + bo1.printBox());
        System.out.println("weight bo1 = " + bo1.getWeight());
        System.out.println("bo2 = " + bo2.printBox());
        System.out.println("weight bo2 = " + bo2.getWeight());
        System.out.println("ba1 = " + ba1.printBox());
        System.out.println("weight ba1 = " + ba1.getWeight());
        System.out.println("ba2 = " + ba2.printBox());
        System.out.println("weight ba2 = " + ba2.getWeight());
        System.out.println("compare bo1 and ba1 : " + bo1.compare(ba1));

    }

    public static <T> void swap(T[] a, int i, int j){
        T c = a[i];
        a[i] = a[j];
        a[j] = c;
    }

    public static <T> ArrayList<T> convertArrayToArrayList(T[] arr){
        ArrayList<T> arrlist= new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            arrlist.add(arr[i]);
        }
        return arrlist;
    }

}

class Fruit {
    float weight;

    public Fruit() {
        this.weight = 0;
    }

    public float getWeight() {
        return weight;
    }

}

class Apple extends Fruit {

    public Apple() {
        super();
        this.weight = 1.0f;
    }
}

class Orange extends Fruit {

    public Orange() {
        super();
        this.weight = 1.5f;
    }
}

class Box <T extends Fruit> {
    ArrayList<T> fruits;

    public Box(ArrayList<T> fruits) {
        this.fruits = fruits;
    }

    public void addFruit(T f) {
        fruits.add(f);
    }

    public float getWeight() {
        float w = 0;
        for (T f: fruits) {
            w += f.getWeight();
        }
        return w;
    }

    public boolean compare(Box b) {
        return Math.abs(getWeight() - b.getWeight()) < 0.00001;
    }

    public void moveFruits(Box<T> b) {
        for (T f: fruits) {
            b.addFruit(f);
        }
        fruits.clear();
    }

    public String printBox() {
        return Arrays.toString(fruits.toArray());
    }

}
