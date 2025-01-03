package tasks;
import java.util.concurrent.*;

public class Task3 {


    public static void longRunningTask(CancellationToken token) {
        for (int i = 0; i < 10; i++) {
            if (token.isCancelled()) {
                System.out.println("Task was cancelled!");
                return;
            }
            try {
                Thread.sleep(1000); // Симуляція роботи
                System.out.println("Task is running... Step " + (i + 1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Task was interrupted!");
                return;
            }
        }
        System.out.println("Task completed successfully.");
    }


    static class CancellationToken {
        private volatile boolean cancelled = false;

        public void cancel() {
            cancelled = true;
        }

        public boolean isCancelled() {
            return cancelled;
        }
    }

    public static void main(String[] args) {

        CancellationToken token = new CancellationToken();


        ExecutorService executorService = Executors.newSingleThreadExecutor();


        Callable<Void> task = () -> {
            longRunningTask(token);
            return null;
        };


        Future<Void> future = executorService.submit(task);

        try {
            Thread.sleep(3000);
            token.cancel();
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error during execution");
        }

        executorService.shutdown();
    }
}
