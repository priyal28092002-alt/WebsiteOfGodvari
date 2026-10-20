package com.example.collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Highlights lesser-known helpers in java.util.Collections.
 */
public class AdvancedCollectionsUtilitiesDemo {
    public static void main(String[] args) {
        System.out.println("=== Advanced Collections Utilities ===\n");
        frequencyAndDisjoint();
        checkedCollections();
        newSetFromMap();
        asLifoQueueExample();
        replaceAllAndSwap();
    }

    private static void frequencyAndDisjoint() {
        System.out.println("-- frequency(), disjoint(), and subList searches --");
        List<String> workflow = new ArrayList<>(List.of("START", "VALIDATE", "ENRICH", "PERSIST", "VALIDATE", "END"));
        int validateCount = Collections.frequency(workflow, "VALIDATE");
        System.out.println("VALIDATE appears " + validateCount + " times");

        List<String> qualitySteps = List.of("QC", "SIGN_OFF");
        boolean disjoint = Collections.disjoint(workflow, qualitySteps);
        System.out.println("Workflow disjoint from quality steps: " + disjoint);

        int firstOccurrence = Collections.indexOfSubList(workflow, List.of("ENRICH", "PERSIST"));
        int lastOccurrence = Collections.lastIndexOfSubList(workflow, List.of("VALIDATE"));
        System.out.println("First ENRICH->PERSIST at index " + firstOccurrence);
        System.out.println("Last VALIDATE at index " + lastOccurrence + "\n");
    }

    private static void checkedCollections() {
        System.out.println("-- checkedList and checkedMap guard against raw types --");
        List<String> environments = Collections.checkedList(new ArrayList<>(), String.class);
        environments.add("DEV");
        environments.add("QA");
        System.out.println("Checked list contents: " + environments);

        Map<String, Integer> releasesPerYear = Collections.checkedMap(new java.util.HashMap<>(), String.class, Integer.class);
        releasesPerYear.put("2023", 6);
        releasesPerYear.put("2024", 8);
        System.out.println("Checked map contents: " + releasesPerYear + "\n");
    }

    private static void newSetFromMap() {
        System.out.println("-- newSetFromMap creates lock-free concurrent sets --");
        Set<String> tenants = Collections.newSetFromMap(new ConcurrentHashMap<>());
        tenants.addAll(List.of("Retail", "Banking", "Healthcare"));
        System.out.println("Concurrent set snapshot: " + tenants);
        System.out.println("Contains Banking? " + tenants.contains("Banking") + "\n");
    }

    private static void asLifoQueueExample() {
        System.out.println("-- asLifoQueue() views Deque as stack --");
        Deque<String> migrations = new ArrayDeque<>();
        migrations.add("baseline");
        migrations.add("seed-data");
        var stack = Collections.asLifoQueue(migrations);
        stack.add("feature-123");
        stack.add("hotfix-9");
        while (!stack.isEmpty()) {
            System.out.println("Applying migration " + stack.remove());
        }
        System.out.println();
    }

    private static void replaceAllAndSwap() {
        System.out.println("-- replaceAll, rotate, and swap to manipulate lists --");
        List<String> releases = new ArrayList<>(List.of("1.0", "1.1", "1.2", "2.0"));
        Collections.replaceAll(releases, "1.1", "1.1.1");
        Collections.rotate(releases, 1);
        Collections.swap(releases, 0, releases.size() - 1);
        System.out.println("Adjusted release plan: " + releases + "\n");
    }
}
