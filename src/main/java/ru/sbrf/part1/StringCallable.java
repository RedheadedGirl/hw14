package ru.sbrf.part1;

import ru.sbrf.part1.exception.CallableException;

class StringCallable<T> extends CallableArgumentable<T> {

    private String word;

    public StringCallable(String word) {
        this.word = word;
    }

    @Override
    public Object getArgument() {
        return word;
    }

    @Override
    public T call() {
        for (int i = 1; i < 6; i++) { // этот цикл тут чисто чтобы показать что выполнение ничем не прерывается
            System.out.println(Thread.currentThread().getName() + ". Execution " + i);
        }
        try {
            return (T) word.toUpperCase();
        } catch (Exception e) {
            throw new CallableException("Просчет StringCallable завершился с ошибкой", e);
        }
    }
}
