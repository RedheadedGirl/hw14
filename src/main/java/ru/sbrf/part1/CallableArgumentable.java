package ru.sbrf.part1;

import java.util.concurrent.Callable;

public class CallableArgumentable<T> implements Argumentable, Callable<T> {

    @Override
    public T call() throws Exception {
        return null;
    }

    @Override
    public Object getArgument() {
        return null;
    }
}
