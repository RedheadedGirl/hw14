package ru.sbrf.part1;

import org.junit.jupiter.api.Test;
import ru.sbrf.part1.exception.CallableException;

import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CallableTest {

    @Test
    void givenStringCallable_whenStart_thenCorrectValue() {
        Thread thread = new Thread(() -> {
            StringCallable stringCallable = new StringCallable("hello");
            Task<String> task = new Task<>(stringCallable, new ReentrantLock());
            String result = "";
            try {
                result = task.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals("HELLO", result);

        });
        thread.start();
    }

    @Test
    void givenIntCallable_whenStart_thenCorrectValue() {
        Thread thread = new Thread(() -> {
            MultiplyingCallable multiplyingCallable = new MultiplyingCallable(5);
            Task<Integer> task = new Task<>(multiplyingCallable, new ReentrantLock());
            int result;
            try {
                result = task.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals(10, result);
        });
        thread.start();
    }

    @Test
    void givenIntCallableWithNull_whenStart_thenThrow() {
        Thread thread = new Thread(() -> {
            MultiplyingCallable multiplyingCallable = new MultiplyingCallable(null);
            Task<String> task = new Task<>(multiplyingCallable, new ReentrantLock());
            CallableException callableException = assertThrows(CallableException.class, task::get);
            assertEquals("Просчет MultiplyingCallable завершился с ошибкой", callableException.getMessage());
            assertEquals(NullPointerException.class, callableException.getCause().getClass());
        });
        thread.start();
    }
}
