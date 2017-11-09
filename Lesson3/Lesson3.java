/**
 * Java 3. Lesson 3.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-10-19
 */

package ru.geekbrains.java3.homework;

import java.io.*;
import java.util.*;

public class Lesson3 {
    public static void main(String[] args) {

        try {
            task1();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            task2();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            task3();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void task1() throws IOException {
        FileInputStream  fis;
        ByteArrayOutputStream baos;
        fis = new FileInputStream("task1.txt");
        baos = new ByteArrayOutputStream();
        int a;
        while ((a = fis.read()) != -1) {
            baos.write(a);
        }
        byte[] arr = baos.toByteArray();
        for (int i = 0; i < arr.length ; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void task2() throws IOException {
        ArrayList<InputStream> al = new ArrayList<>();
        al.add(new FileInputStream("task2_1.txt"));
        al.add(new FileInputStream("task2_2.txt"));
        al.add(new FileInputStream("task2_3.txt"));
        al.add(new FileInputStream("task2_4.txt"));
        al.add(new FileInputStream("task2_5.txt"));
        Enumeration<InputStream> e = Collections.enumeration(al);
        SequenceInputStream sis = new SequenceInputStream(e);
        FileOutputStream fos;
        fos = new FileOutputStream("task2.txt");
        int a;
        while ((a = sis.read()) != -1) {
            fos.write(a);
        }
    }

    public static void task3() throws IOException {
        final int PAGESIZE = 1800;
        RandomAccessFile raf;
        raf = new RandomAccessFile("task3.txt", "r");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter page number (number <= 0 - exit):");
        int a = Integer.parseInt(reader.readLine());
        while (a > 0) {
            raf.seek((a - 1) * PAGESIZE);
            byte[] arr = new byte[PAGESIZE];
            raf.read(arr, 0, PAGESIZE);
            for (int i = 0; i < PAGESIZE; i++) {
                System.out.print((char) arr[i]);
            }
            System.out.println();
            System.out.println("Enter page number (number <= 0 - exit):");
            a = Integer.parseInt(reader.readLine());
        }
    }
}
