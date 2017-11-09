/**
 * Java 3. Lesson 4.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-10-24
 */


package ru.geekbrains.java3.homework;

import java.io.*;
import java.util.ArrayList;

public class Lesson4 {
    public static void main(String[] args) {
        //Task1
        new Task1();
        System.out.println();

        //Task2
        new Task2();
        System.out.println();

        //Task3
        new MFU();
        System.out.println();
    }
}

class Task1{
    char lastChar = 'C';

    Task1() {
        PrintCharQueue pCh = new PrintCharQueue();
        PrintChar a = new PrintChar('A', pCh);
        PrintChar b = new PrintChar('B', pCh);
        PrintChar c = new PrintChar('C', pCh);
        Thread ta = new Thread(a);
        ta.start();
        Thread tb = new Thread(b);
        tb.start();
        Thread tc = new Thread(c);
        tc.start();
        try {
            ta.join();
            tb.join();
            tc.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class PrintCharQueue {
        synchronized void printChar(char ch) {
            while (!((ch == 'A' && lastChar == 'C') || (ch == 'B' && lastChar == 'A') ||
                     (ch == 'C' && lastChar == 'B'))) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(ch);
            lastChar = ch;
            notifyAll();
        }
    }

    class PrintChar implements Runnable {
        PrintCharQueue pCh;
        char ch;

        public PrintChar(char ch, PrintCharQueue pCh) {
            this.ch = ch;
            this.pCh = pCh;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                pCh.printChar(ch);
            }
        }
    }
}

class Task2 {
    FileWriter fw;

    Task2() {
        try {
            fw = new FileWriter("task2.txt");
            Thread t1 = new Thread(new WriteToFile("Thread 1"));
            Thread t2 = new Thread(new WriteToFile("Thread 2"));
            Thread t3 = new Thread(new WriteToFile("Thread 3"));
            t1.start();
            t2.start();
            t3.start();
            try {
                t1.join();
                t2.join();
                t3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class WriteToFile implements Runnable {
        String name;

        public WriteToFile(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                write();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
            }
        }

        synchronized public void write(){
            try {
                fw.write(name + "\n");
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


class MFU {
    Object print = new Object();
    Object scan = new Object();
    int pagePrint = 0;
    int pageScan = 0;
    ArrayList<Thread> al = new ArrayList<>();

    public MFU() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Page " + (i + 1) + " ready to print");
            Thread t = new Thread(() -> this.printOperation());
            al.add(t);
            t.start();
        }
        for (int i = 0; i < 100; i++) {
            System.out.println("Page " + (i + 1) + " ready to scan");
            Thread t = new Thread(() -> this.scanOperation());
            al.add(t);
            t.start();
        }
        try {
            for (Thread t : al) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void printOperation() {
        synchronized (print) {
            pagePrint++;
            System.out.println("Page " + pagePrint + " is printing");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Page " + pagePrint + " has been printed");
        }
    }

    void scanOperation() {
        synchronized (scan) {
            pageScan++;
            System.out.println("Page " + pageScan + " is scanning");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Page " + pageScan + " has been scanned");
        }
    }

}


