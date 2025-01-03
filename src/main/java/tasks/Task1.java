package tasks;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class Task1 {
    public static <T, R> void asyncMap(List<T> input, Function<T, R> mapper, Consumer<List<R>> callback) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<CompletableFuture<R>> futures = input.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> mapper.apply(item), executor))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()))
                .thenAccept(callback)
                .whenComplete((r, e) -> executor.shutdown());
    }

    public static void main(String[] args) {
        asyncMap(Arrays.asList(1, 2, 3), x -> x * 2, result -> System.out.println("Async Map Result: " + result));
    }
}
