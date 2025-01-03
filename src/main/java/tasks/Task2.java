package tasks;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class Task2 {
    public static <T, R> CompletableFuture<List<R>> promiseMap(List<T> input, Function<T, R> mapper) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<CompletableFuture<R>> futures = input.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> mapper.apply(item), executor))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()))
                .whenComplete((r, e) -> executor.shutdown());
    }

    public static void main(String[] args) {
        promiseMap(Arrays.asList(4, 5, 6), x -> x * 3)
                .thenAccept(result -> System.out.println("Promise Result: " + result));
    }
}
