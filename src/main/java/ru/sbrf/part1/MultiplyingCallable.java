package ru.sbrf.part1;

import ru.sbrf.part1.exception.CallableException;

class MultiplyingCallable<T> extends CallableArgumentable<T> {

    private Integer number;

    public MultiplyingCallable(Integer number) {
        this.number = number;
    }

    @Override
    public Object getArgument() {
        return number;
    }

    @Override
    public T call() {
        for (int i = 1; i < 6; i++) { // этот цикл тут чисто чтобы показать что выполнение ничем не прерывается
            System.out.println(Thread.currentThread().getName() + ". Execution " + i);
        }
        try {
            number = number * 2;
            return (T) number;
        } catch (Exception e) {
            throw new CallableException("Просчет MultiplyingCallable завершился с ошибкой", e);
        }
    }
}
