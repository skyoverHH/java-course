package Thread;

import java.util.LinkedList;

public class MyThreadPool {

    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private boolean isShutdown = false;

    public MyThreadPool(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Неверное количество");
        }

        for (int i = 0; i < capacity; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    Runnable task;

                    synchronized (taskQueue) {
                        while (taskQueue.isEmpty() && !isShutdown) {
                            try {
                                taskQueue.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }

                        if (taskQueue.isEmpty() && isShutdown) {
                            return;
                        }

                        task = taskQueue.removeFirst();
                    }

                    task.run();
                }
            });

            thread.start();
        }
    }

    public void execute(Runnable task) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("Пул уже остановлен");
            }

            taskQueue.addLast(task);
            taskQueue.notifyAll();
        }
    }

    public void shutdown() {
        synchronized (taskQueue) {
            isShutdown = true;
            taskQueue.notifyAll();
        }
    }
}