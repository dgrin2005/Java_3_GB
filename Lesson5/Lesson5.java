/**
 * Java 3. Lesson 5.
 *
 *  @author Dmitry Grinshteyn
 *  @version dated 2017-10-29
 */

package ru.geekbrains.java3.homework;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Lesson5 {
    public static final int CARS_QUANTITY = 4;

    public static void main (String[] args) {
        new Competition(CARS_QUANTITY);
    }
}

class Competition {
    private int carsQuantity;
    private CyclicBarrier readyCB;
    private CountDownLatch startCDL = new CountDownLatch (1);
    private CountDownLatch finishCDL;
    private Lock lock;
    private Semaphore tunnelS;
    private int CARS_COUNT = 0;
    private ArrayList<Thread> arrT = new ArrayList<>();

    public Competition(int carsQuantity) {
        this.carsQuantity = carsQuantity;
        finishCDL = new CountDownLatch (carsQuantity);
        lock = new ReentrantLock();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race (new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[this.carsQuantity];
        readyCB = new CyclicBarrier(this.carsQuantity + 1);
        tunnelS = new Semaphore (this.carsQuantity / 2);
        for (int i = 0; i < cars.length; i ++) {
            cars[i] = new Car(race, 20 + (int)(Math.random() * 10));
        }
        try {
            for (int i = 0 ; i < cars.length; i ++) {
                Thread t = new Thread(cars[i]);
                arrT.add(t);
                t.start();
            }
            readyCB.await();
            startCDL.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            finishCDL.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    class Car implements Runnable {
        private Race race;
        private int speed;
        private String name;

        public String getName() {
            return name;
        }

        public int getSpeed() {
            return speed;
        }

        public Car(Race race, int speed) {
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int)(Math.random () * 800));
                System.out.println(this.name + " готов");
                readyCB.await();
                startCDL.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < race.getStages().size (); i++) {
                race.getStages().get(i).go(this);
            }
            System.out.println(this.name + " закончил гонку");
            lock.lock();
            if (finishCDL.getCount() == carsQuantity) {
                System.out.println(this.name + " победил");
            }
            finishCDL.countDown();
            lock.unlock();
        }
    }

    abstract class Stage {
        protected int length;
        protected String description;

        public String getDescription() {
            return description;
        }

        public abstract void go(Car c);
    }

    class Road extends Stage {
        public Road(int length) {
            this.length = length ;
            this.description = "Дорога " + length + " метров";
        }

        @Override
        public void go(Car c) {
            try {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Tunnel extends Stage{
        public Tunnel() {
            this.length = 80;
            this.description = "Тоннель " + length + " метров";
        }

        @Override
        public void go(Car c) {
            try {
                try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    tunnelS.acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c . getSpeed() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(c.getName() + " закончил этап: " + description);
                    tunnelS.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Race {
        private ArrayList<Stage> stages;

        public ArrayList<Stage> getStages() {
            return stages;
        }

        public Race(Stage ... stages) {
            this.stages = new ArrayList<>(Arrays.asList(stages));
        }
    }
}