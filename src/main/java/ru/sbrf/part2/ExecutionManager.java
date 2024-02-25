package ru.sbrf.part2;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}

