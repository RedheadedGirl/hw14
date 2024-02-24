package ru.sbrf.part1;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> {

    private static HashMap<Object, Object> map = new HashMap<>();

    private CallableArgumentable<? extends T> callable;
    private ReentrantLock locker;

    public Task(CallableArgumentable<? extends T> callable, ReentrantLock locker) {
        this.callable = callable;
        this.locker = locker;
    }

    public T get() throws Exception {
        Optional<T> fromMapIfPresent = returnFromMapIfPresent();
        if (fromMapIfPresent.isPresent()) {
            return fromMapIfPresent.get();
        }

        locker.lock(); // устанавливаем блокировку
        try {
            Object argument = callable.getArgument();
            T result = callable.call();
            if (argument != null) {
                map.put(argument, result);
            }
            return result;
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            locker.unlock(); // снимаем блокировку
        }
        return null;
    }

    private Optional<T> returnFromMapIfPresent() {
        Object argument = callable.getArgument();
        if (map.get(argument) != null) {
            System.out.println("from cache");
            return Optional.of((T) map.get(argument));
        }
        return Optional.empty();
    }
}

