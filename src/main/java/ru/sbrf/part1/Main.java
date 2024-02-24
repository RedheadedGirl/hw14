package ru.sbrf.part1;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws Exception {
        ReentrantLock locker = new ReentrantLock(); // создаем заглушку
        for (int i = 1; i < 4; i++) { // при запуске видим, что выполнение происходит в одно время только в одном потоке благодаря ReentrantLock
            Thread t = createThreadWithStringTask(String.valueOf(i), locker);
            t.setName("Thread " + i);
            t.start();
        }

        Thread.sleep(2000);

        // этот поток так же отработает, ведь с таким значением callable еще не вызывался
        Thread t = createThreadWithStringTask("hello9", locker);
        t.setName("Thread " + 9);
        t.start();

        Thread.sleep(2000);

        // этот поток не будет вызывать метод, так как в кеше уже есть значение для hello3
        Thread t2 = createThreadWithStringTask("hello3", locker);
        t2.setName("Thread " + 3);
        t2.start();


        // не строковое задание, а интовое
        // первый вызов
        Thread t4 = createThreadWithIntTask(4, locker);
        t4.setName("Thread " + 4);
        t4.start();

        Thread.sleep(2000);

        // этот поток не будет вызывать метод, так как в кеше уже есть значение для 4
        Thread t5 = createThreadWithIntTask(4, locker);
        t5.setName("Thread " + 5);
        t5.start();
    }

    public static Thread createThreadWithStringTask(String name, ReentrantLock locker) {
        return new Thread(() -> {
            StringCallable stringCallable = new StringCallable("hello" + name);
            Task<String> task = new Task<>(stringCallable, locker);
            try {
                System.out.println(task.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Thread createThreadWithIntTask(int number, ReentrantLock locker) {
        return new Thread(() -> {
            MultiplyingCallable myCallable = new MultiplyingCallable<Integer>(number);
            Task<Integer> task = new Task<Integer>(myCallable, locker);
            try {
                System.out.println(task.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}


