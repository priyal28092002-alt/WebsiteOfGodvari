package com.example.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Demonstrates Set implementations with Collections helpers.
 */
public class SetCollectionsDemo {
    public static void main(String[] args) {
        System.out.println("=== Collections with Set ===\n");

        Set<Integer> hashSet = new HashSet<>(Arrays.asList(5, 1, 3, 3, 2, 4, 5));
        System.out.println("HashSet (no guaranteed order): " + hashSet);

        Set<Integer> linkedHashSet = new LinkedHashSet<>(Arrays.asList(5, 1, 3, 3, 2, 4, 5));
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);

        Set<Integer> treeSet = new TreeSet<>(hashSet);
        System.out.println("TreeSet (natural order): " + treeSet);

        Set<Integer> descendingSet = new TreeSet<>(Collections.reverseOrder());
        descendingSet.addAll(hashSet);
        System.out.println("TreeSet with reverse order comparator: " + descendingSet);

        boolean containsAll = hashSet.containsAll(Arrays.asList(1, 2));
        System.out.println("HashSet contains 1 and 2: " + containsAll);

        Set<Integer> copy = new HashSet<>(hashSet);
        copy.removeAll(Collections.singleton(3));
        System.out.println("After removing 3s: " + copy);

        Set<Integer> intersection = new HashSet<>(hashSet);
        intersection.retainAll(new TreeSet<>(Arrays.asList(2, 3, 6)));
        System.out.println("Intersection with {2,3,6}: " + intersection);

        Set<Integer> unmodifiable = Collections.unmodifiableSet(treeSet);
        System.out.println("Unmodifiable TreeSet: " + unmodifiable);
    }
}
