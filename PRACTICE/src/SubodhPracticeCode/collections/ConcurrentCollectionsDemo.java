package com.example.collections;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Showcases thread-safe collections and how they behave under concurrent access.
 */
public class ConcurrentCollectionsDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Concurrent Collections Demo ===\n");
        runConcurrentHashMapExample();
        runCopyOnWriteListExample();
        runBlockingQueueExample();
        runConcurrentQueueExample();
    }

    private static void runConcurrentHashMapExample() throws InterruptedException {
        System.out.println("-- ConcurrentHashMap with computeIfAbsent --");
        Map<String, Integer> pageHits = new ConcurrentHashMap<>();
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<String> urls = List.of("/home", "/docs", "/login", "/home", "/docs", "/pricing");

        for (int i = 0; i < 5; i++) {
            pool.submit(() -> urls.forEach(url -> pageHits.merge(url, 1, Integer::sum)));
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        String summary = pageHits.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", "));
        System.out.println("Aggregated hits: " + summary + "\n");
    }

    private static void runCopyOnWriteListExample() {
        System.out.println("-- CopyOnWriteArrayList iteration without ConcurrentModificationException --");
        CopyOnWriteArrayList<String> stages = new CopyOnWriteArrayList<>(List.of("QUEUED", "RUNNING"));
        stages.add("COMPLETE");

        stages.forEach(stage -> {
            System.out.println("Observing stage: " + stage);
            if ("RUNNING".equals(stage)) {
                stages.add("VERIFIED");
            }
        });
        System.out.println("List after iteration: " + stages + "\n");
    }

    private static void runBlockingQueueExample() throws InterruptedException {
        System.out.println("-- ArrayBlockingQueue with producer/consumer timing --");
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Instant started = Instant.now();

        pool.submit(() -> {
            try {
                List.of("task-1", "task-2", "task-3").forEach(task -> {
                    try {
                        queue.put(task);
                        System.out.println("Produced " + task + " (queue size=" + queue.size() + ")");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                queue.put("DONE");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pool.submit(() -> {
            try {
                String item;
                while (!(item = queue.take()).equals("DONE")) {
                    System.out.println("Consumed " + item);
                    TimeUnit.MILLISECONDS.sleep(150);
                }
                queue.put("DONE");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("BlockingQueue run duration: " + Duration.between(started, Instant.now()).toMillis() + " ms\n");
    }

    private static void runConcurrentQueueExample() {
        System.out.println("-- ConcurrentLinkedQueue snapshot --");
        ConcurrentLinkedQueue<String> auditTrail = new ConcurrentLinkedQueue<>();
        auditTrail.addAll(List.of("LOGIN", "VIEW_PAGE", "LOGOUT"));
        auditTrail.removeIf(event -> event.startsWith("VIEW"));
        System.out.println("Remaining events: " + auditTrail + "\n");
    }
}
