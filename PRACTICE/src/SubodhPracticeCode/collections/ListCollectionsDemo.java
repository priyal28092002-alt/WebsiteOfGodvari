package com.example.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Demonstrates how java.util.Collections works with List implementations.
 */
public class ListCollectionsDemo {
    public static void main(String[] args) {
        System.out.println("=== Collections with List ===\n");

        List<String> cities = new ArrayList<>();
        Collections.addAll(cities, "Delhi", "Mumbai", "Bengaluru", "Kolkata", "Chennai");
        System.out.println("Original list: " + cities);

        Collections.shuffle(cities, new Random(42));
        System.out.println("Shuffled (seed=42): " + cities);

        Collections.sort(cities);
        System.out.println("Sorted ascending: " + cities);

        Collections.sort(cities, Comparator.reverseOrder());
        System.out.println("Sorted descending: " + cities);

        Collections.reverse(cities);
        System.out.println("Reversed order: " + cities);

        Collections.rotate(cities, 2);
        System.out.println("Rotated by 2: " + cities);

        int idx = Collections.binarySearch(cities, "Kolkata", Comparator.naturalOrder());
        System.out.println("Binary search for Kolkata: index " + idx);

        List<String> copyTarget = new ArrayList<>(Collections.nCopies(cities.size(), ""));
        Collections.copy(copyTarget, cities);
        System.out.println("Copied list: " + copyTarget);

        Collections.fill(copyTarget, "City");
        System.out.println("Filled list: " + copyTarget);

        List<String> immutable = Collections.unmodifiableList(cities);
        System.out.println("Unmodifiable view: " + immutable);

        List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>(cities));
        synchronized (synchronizedList) {
            System.out.println("Synchronized view snapshot: " + synchronizedList);
        }
    }
}
