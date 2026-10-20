package com.example.collections;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Demonstrates Map implementations and Collections helpers.
 */
public class MapCollectionsDemo {
    public static void main(String[] args) {
        System.out.println("=== Collections with Map ===\n");

        Map<Integer, String> hashMap = new java.util.HashMap<>();
        hashMap.put(3, "Three");
        hashMap.put(1, "One");
        hashMap.put(2, "Two");
        hashMap.put(1, "One (updated)");
        System.out.println("HashMap (no order): " + hashMap);

        Map<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.putAll(hashMap);
        linkedHashMap.put(4, "Four");
        System.out.println("LinkedHashMap (insertion order): " + linkedHashMap);

        Map<Integer, String> treeMap = new TreeMap<>(hashMap);
        System.out.println("TreeMap (sorted keys): " + treeMap);

        Map<Integer, String> customOrder = new TreeMap<>(Collections.reverseOrder());
        customOrder.putAll(hashMap);
        customOrder.put(0, "Zero");
        System.out.println("TreeMap (reverse order): " + customOrder);

        System.out.println("Traverse entries:");
        hashMap.forEach((key, value) -> System.out.println("  key=" + key + ", value=" + value));

        Map<Integer, String> syncMap = Collections.synchronizedMap(new java.util.HashMap<>(hashMap));
        synchronized (syncMap) {
            System.out.println("Synchronized map snapshot: " + syncMap);
        }

        Map<Integer, String> immutableMap = Collections.unmodifiableMap(treeMap);
        System.out.println("Unmodifiable TreeMap view: " + immutableMap);
    }
}
