package com.example.streams;

import java.util.*;
import java.util.stream.*;

/**
 * Stream API Quick Reference and Cheat Sheet
 * Demonstrates common patterns and syntax
 */
public class StreamAPICheatSheet {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> words = Arrays.asList("Java", "Stream", "API", "Demo");

        System.out.println("========== STREAM API CHEAT SHEET ==========\n");

        // BASIC OPERATIONS
        System.out.println("1. BASIC OPERATIONS");
        System.out.println("   Count: " + numbers.stream().count());
        System.out.println("   Sum: " + numbers.stream().reduce(0, Integer::sum));
        System.out.println("   Max: " + numbers.stream().max(Integer::compare).orElse(0));
        System.out.println("   Min: " + numbers.stream().min(Integer::compare).orElse(0));

        // FILTERING
        System.out.println("\n2. FILTERING");
        System.out.println("   Even numbers: " + numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList()));
        System.out.println("   Odd numbers: " + numbers.stream()
            .filter(n -> n % 2 != 0)
            .collect(Collectors.toList()));

        // TRANSFORMATION
        System.out.println("\n3. TRANSFORMATION (MAP)");
        System.out.println("   Doubled: " + numbers.stream()
            .map(n -> n * 2)
            .collect(Collectors.toList()));
        System.out.println("   Squared: " + numbers.stream()
            .map(n -> n * n)
            .collect(Collectors.toList()));
        System.out.println("   To String: " + words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList()));

        // FLATTENING
        System.out.println("\n4. FLATTENING (FLATMAP)");
        List<List<Integer>> nested = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4),
            Arrays.asList(5, 6)
        );
        System.out.println("   Flattened: " + nested.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList()));

        // SORTING
        System.out.println("\n5. SORTING");
        System.out.println("   Ascending: " + numbers.stream()
            .sorted()
            .collect(Collectors.toList()));
        System.out.println("   Descending: " + numbers.stream()
            .sorted(Collections.reverseOrder())
            .collect(Collectors.toList()));
        System.out.println("   By length: " + words.stream()
            .sorted(Comparator.comparingInt(String::length))
            .collect(Collectors.toList()));

        // DISTINCT AND LIMIT
        System.out.println("\n6. DISTINCT & LIMIT");
        List<Integer> withDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        System.out.println("   Distinct: " + withDuplicates.stream()
            .distinct()
            .collect(Collectors.toList()));
        System.out.println("   First 5: " + numbers.stream()
            .limit(5)
            .collect(Collectors.toList()));
        System.out.println("   Skip 3, take 4: " + numbers.stream()
            .skip(3)
            .limit(4)
            .collect(Collectors.toList()));

        // MATCHING
        System.out.println("\n7. MATCHING (PREDICATES)");
        System.out.println("   Any > 5? " + numbers.stream().anyMatch(n -> n > 5));
        System.out.println("   All > 0? " + numbers.stream().allMatch(n -> n > 0));
        System.out.println("   None < 0? " + numbers.stream().noneMatch(n -> n < 0));

        // FINDING
        System.out.println("\n8. FINDING");
        System.out.println("   First > 5: " + numbers.stream()
            .filter(n -> n > 5)
            .findFirst().orElse(-1));
        System.out.println("   Any match: " + words.stream()
            .filter(w -> w.startsWith("S"))
            .findAny().orElse("Not found"));

        // COLLECTING
        System.out.println("\n9. COLLECTING");
        System.out.println("   To List: " + numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList()));
        System.out.println("   To Set: " + withDuplicates.stream()
            .collect(Collectors.toSet()));
        System.out.println("   To Map: " + numbers.stream()
            .filter(n -> n <= 5)
            .collect(Collectors.toMap(n -> n, n -> n * n)));
        System.out.println("   Joined: " + words.stream()
            .collect(Collectors.joining(", ")));

        // STATISTICS
        System.out.println("\n10. STATISTICS (PRIMITIVE STREAMS)");
        IntSummaryStatistics stats = numbers.stream()
            .mapToInt(Integer::intValue)
            .summaryStatistics();
        System.out.println("   Sum: " + stats.getSum());
        System.out.println("   Average: " + stats.getAverage());
        System.out.println("   Max: " + stats.getMax());
        System.out.println("   Min: " + stats.getMin());
        System.out.println("   Count: " + stats.getCount());

        // GROUPING
        System.out.println("\n11. GROUPING");
        Map<Integer, List<Integer>> grouped = numbers.stream()
            .collect(Collectors.groupingBy(n -> n % 2));
        System.out.println("   By modulo 2: " + grouped);

        // PARTITIONING
        System.out.println("\n12. PARTITIONING");
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("   Even/Odd: " + partitioned);

        // OPTIONAL OPERATIONS
        System.out.println("\n13. OPTIONAL HANDLING");
        Optional<Integer> opt1 = numbers.stream()
            .filter(n -> n > 100)
            .findFirst();
        System.out.println("   OrElse: " + opt1.orElse(-1));
        System.out.println("   OrElseGet: " + opt1.orElseGet(() -> -999));

        // PEEKING FOR DEBUG
        System.out.println("\n14. DEBUG WITH PEEK");
        numbers.stream()
            .filter(n -> n > 5)
            .peek(n -> System.out.print(n + " "))
            .limit(3)
            .forEach(x -> {});
        System.out.println();

        // PERFORMANCE: PARALLEL vs SEQUENTIAL
        System.out.println("\n15. PERFORMANCE COMPARISON");
        long start = System.nanoTime();
        long seqSum = numbers.stream().mapToLong(Integer::longValue).sum();
        long seqTime = System.nanoTime() - start;

        start = System.nanoTime();
        long parSum = numbers.parallelStream().mapToLong(Integer::longValue).sum();
        long parTime = System.nanoTime() - start;

        System.out.println("   Sequential: " + seqSum + " (" + seqTime + "ns)");
        System.out.println("   Parallel: " + parSum + " (" + parTime + "ns)");
        System.out.println("   Note: Parallel is slower for small datasets!");
    }
}

