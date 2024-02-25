package ru.sbrf.part2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context {

    private final ExecutorService executorService;
    public final AtomicInteger completedTasks;
    public final AtomicInteger interruptedTasks;
    public final AtomicInteger failedTasks;

    public ContextImpl(ExecutorService executorService) {
        this.executorService = executorService;
        completedTasks = new AtomicInteger();
        interruptedTasks = new AtomicInteger();
        failedTasks = new AtomicInteger();
    }

    @Override
    public int getCompletedTaskCount() {
        return completedTasks.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failedTasks.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTasks.get();
    }

    @Override
    public void interrupt() {
        interruptedTasks.incrementAndGet();
        executorService.shutdown();
    }

    @Override
    public boolean isFinished() { // если есть упавшие таски, то false
        return failedTasks.get() == 0;
    }
}
