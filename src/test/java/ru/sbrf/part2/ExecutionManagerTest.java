package ru.sbrf.part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutionManagerTest {

    @Test
    void givenGoodTasksAndOneBad_whenExecute_ThenCorrect() {
        ExecutionManagerImpl manager = new ExecutionManagerImpl();
        Context context = manager.execute(createTask("callback"), createTask("1"), createTask("2"),
                createBadTask("bad"));
        assertEquals(2, context.getCompletedTaskCount());
        assertEquals(1, context.getFailedTaskCount());
        assertEquals(0, context.getInterruptedTaskCount());
        assertFalse(context.isFinished());
    }

    @Test
    void givenTasks_whenInterrupt_ThenCorrect() {
        ExecutionManagerImpl manager = new ExecutionManagerImpl();
        Context context = manager.execute(createTask("callback"), new Thread(() -> {
            System.out.println("started");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("finished");
        }));
        context.interrupt();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(context.isFinished());
        assertEquals(0, context.getCompletedTaskCount());
        assertEquals(0, context.getFailedTaskCount());
        assertEquals(1, context.getInterruptedTaskCount());
    }

    private Thread createTask(String name) {
        return new Thread(() -> {
            System.out.println("started " + name);
            System.out.println("finished " + name);
        });
    }

    private Thread createBadTask(String name) {
        return new Thread(() -> {
            System.out.println("started " + name);
            System.out.println(5/0);
            System.out.println("finished " + name);
        });
    }
}
