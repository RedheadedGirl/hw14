package ru.sbrf.part2;

public interface Context {

    /**
     * возвращает количество тасков, которые на текущий момент успешно выполнились
     * @return количество
     */
    int getCompletedTaskCount();

    /**
     * возвращает количество тасков, при выполнении которых произошел Exception
     * @return количество
     */
    int getFailedTaskCount();

    /**
     * отменяет выполнения тасков, которые еще не начали выполняться
     * @return количество
     */
    int getInterruptedTaskCount();

    /**
     * возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода)
     * @return количество
     */
    void interrupt();

    /**
     * вернет true, если все таски были выполнены или отменены, false в противном случае
     * @return количество
     */
    boolean isFinished();
}

