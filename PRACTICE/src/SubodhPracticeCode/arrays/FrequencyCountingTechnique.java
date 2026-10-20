package com.example.arrays;

import java.util.*;

/**
 * CORE CONCEPT: Frequency Counting and Hashing
 * 
 * Use when: Counting occurrences, finding duplicates, frequency analysis
 * Time Complexity: O(n) with HashMap/array as frequency counter
 * Space Complexity: O(k) where k = unique elements
 * 
 * Pattern Recognition:
 * - Find duplicate/missing elements
 * - Count frequency of elements
 * - First non-repeating element
 * - Elements appearing k times
 * - Majority element (appearing > n/2 times)
 * 
 * Key Techniques:
 * 1. HashMap for general counting
 * 2. Array as hashmap when range is known (e.g., 1 to n)
 * 3. Moore's Voting Algorithm for majority element
 * 
 * Applications:
 * - Finding missing/duplicate numbers
 * - Anagram grouping
 * - Subarray sum problems
 */
public class FrequencyCountingTechnique {
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 2, 1, 4, 5, 4, 6};
        System.out.println("Array: " + Arrays.toString(arr));
        
        // Count frequencies
        Map<Integer, Integer> freq = countFrequencies(arr);
        System.out.println("\nFrequencies: " + freq);
        
        // Find duplicates
        System.out.println("Duplicates: " + findDuplicates(arr));
        
        // First non-repeating
        System.out.println("First non-repeating: " + firstNonRepeating(arr));
        
        // Missing and repeating
        int[] arr2 = {1, 2, 3, 3, 5};
        System.out.println("\n\nArray: " + Arrays.toString(arr2));
        int[] result = findMissingAndRepeating(arr2, 5);
        System.out.println("Missing: " + result[0] + ", Repeating: " + result[1]);
        
        // Majority element
        int[] arr3 = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("\n\nArray: " + Arrays.toString(arr3));
        System.out.println("Majority element: " + majorityElement(arr3));
        
        // Top K frequent
        int[] arr4 = {1, 1, 1, 2, 2, 3};
        System.out.println("\n\nArray: " + Arrays.toString(arr4));
        System.out.println("Top 2 frequent: " + topKFrequent(arr4, 2));
        
        // Subarray with sum 0
        int[] arr5 = {4, 2, -3, 1, 6};
        System.out.println("\n\nArray: " + Arrays.toString(arr5));
        System.out.println("Has subarray with sum 0: " + hasSubarrayWithZeroSum(arr5));
    }
    
    /**
     * Count frequency of each element using HashMap
     */
    public static Map<Integer, Integer> countFrequencies(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();
        
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        return freq;
    }
    
    /**
     * Find all duplicate elements
     * Returns list of elements that appear more than once
     */
    public static List<Integer> findDuplicates(int[] arr) {
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        
        for (int num : arr) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }
        
        return new ArrayList<>(duplicates);
    }
    
    /**
     * Find first non-repeating element
     * Concept: Count frequencies, then find first with frequency = 1
     */
    public static int firstNonRepeating(int[] arr) {
        Map<Integer, Integer> freq = new LinkedHashMap<>();
        
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        for (int num : arr) {
            if (freq.get(num) == 1) {
                return num;
            }
        }
        
        return -1;
    }
    
    /**
     * Find missing and repeating number in array [1, n]
     * Technique: Use array indices as hash (mark visited by negation)
     */
    public static int[] findMissingAndRepeating(int[] arr, int n) {
        int[] result = new int[2]; // [missing, repeating]
        
        Map<Integer, Integer> freq = new HashMap<>();
        
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        for (int i = 1; i <= n; i++) {
            int count = freq.getOrDefault(i, 0);
            if (count == 0) {
                result[0] = i; // Missing
            } else if (count > 1) {
                result[1] = i; // Repeating
            }
        }
        
        return result;
    }
    
    /**
     * Majority Element - Moore's Voting Algorithm
     * Element appearing > n/2 times
     * 
     * Concept: Candidate survives if majority, cancels with others
     * Time: O(n), Space: O(1)
     */
    public static int majorityElement(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        
        // Phase 1: Find candidate
        int candidate = arr[0];
        int count = 1;
        
        for (int i = 1; i < arr.length; i++) {
            if (count == 0) {
                candidate = arr[i];
                count = 1;
            } else if (arr[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // Phase 2: Verify candidate (if not guaranteed to exist)
        count = 0;
        for (int num : arr) {
            if (num == candidate) {
                count++;
            }
        }
        
        return count > arr.length / 2 ? candidate : -1;
    }
    
    /**
     * Find top K frequent elements
     * Technique: Frequency map + heap or bucket sort
     */
    public static List<Integer> topKFrequent(int[] arr, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // Use max heap based on frequency
        PriorityQueue<Map.Entry<Integer, Integer>> heap = 
            new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        
        heap.addAll(freq.entrySet());
        
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k && !heap.isEmpty(); i++) {
            result.add(heap.poll().getKey());
        }
        
        return result;
    }
    
    /**
     * Check if subarray with sum = 0 exists
     * Concept: If prefix sum repeats, subarray between them has sum 0
     */
    public static boolean hasSubarrayWithZeroSum(int[] arr) {
        Set<Integer> prefixSums = new HashSet<>();
        prefixSums.add(0);
        
        int sum = 0;
        
        for (int num : arr) {
            sum += num;
            
            if (prefixSums.contains(sum)) {
                return true;
            }
            
            prefixSums.add(sum);
        }
        
        return false;
    }
    
    /**
     * Find longest subarray with equal 0s and 1s
     * Concept: Treat 0 as -1, find longest subarray with sum 0
     */
    public static int longestSubarrayWithEqual0And1(int[] arr) {
        Map<Integer, Integer> sumIndex = new HashMap<>();
        sumIndex.put(0, -1);
        
        int maxLength = 0;
        int sum = 0;
        
        for (int i = 0; i < arr.length; i++) {
            sum += (arr[i] == 0) ? -1 : 1;
            
            if (sumIndex.containsKey(sum)) {
                maxLength = Math.max(maxLength, i - sumIndex.get(sum));
            } else {
                sumIndex.put(sum, i);
            }
        }
        
        return maxLength;
    }
    
    /**
     * Count distinct elements in every window of size k
     */
    public static List<Integer> countDistinctInWindow(int[] arr, int k) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> freq = new HashMap<>();
        
        // First window
        for (int i = 0; i < k; i++) {
            freq.put(arr[i], freq.getOrDefault(arr[i], 0) + 1);
        }
        result.add(freq.size());
        
        // Slide window
        for (int i = k; i < arr.length; i++) {
            // Remove leftmost element
            int left = arr[i - k];
            freq.put(left, freq.get(left) - 1);
            if (freq.get(left) == 0) {
                freq.remove(left);
            }
            
            // Add current element
            freq.put(arr[i], freq.getOrDefault(arr[i], 0) + 1);
            
            result.add(freq.size());
        }
        
        return result;
    }
}
