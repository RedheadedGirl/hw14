package ru.sbrf.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ExecutionManagerImpl implements ExecutionManager {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private List<Future> futures = new ArrayList<>();

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {

        ContextImpl context = new ContextImpl(executor);

        for (Runnable task: tasks) {
            Future<?> submit = executor.submit(task);
            futures.add(submit);
        }

        printStatistics(context);

        do {
            futures.forEach(future -> {
                if (future.state() == Future.State.SUCCESS) {
                    context.completedTasks.incrementAndGet();
                }
                if (future.state() == Future.State.CANCELLED) {
                    context.interruptedTasks.incrementAndGet();
                }
                if (future.state() == Future.State.FAILED) {
                    context.failedTasks.incrementAndGet();
                }
            });
            futures = futures.stream().filter(future -> future.state() == Future.State.RUNNING)
                    .collect(Collectors.toList());
        } while (!futures.isEmpty());

        executor.execute(callback);

        printStatistics(context);

        return context;
    }

    private void printStatistics(ContextImpl context) {
        System.out.println("completed: " + context.getCompletedTaskCount());
        System.out.println("interrupted: " + context.getInterruptedTaskCount());
        System.out.println("failed: " + context.getFailedTaskCount());
    }
}
