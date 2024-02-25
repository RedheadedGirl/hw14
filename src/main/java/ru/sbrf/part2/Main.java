package ru.sbrf.part2;

public class Main {

    public static void main(String[] args) {
        ExecutionManagerImpl manager = new ExecutionManagerImpl();
        manager.execute(createTask("callback"), createTask("1"), createTask("2"),
                createBadTask("bad task"));
    }

    private static Thread createTask(String name) {
        return new Thread(() -> {
            System.out.println("started " + name);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("finished " + name);
        });
    }

    private static Thread createBadTask(String name) {
        return new Thread(() -> {
            System.out.println("started " + name);
            System.out.println(2/0);
            System.out.println("finished " + name);
        });
    }
}
