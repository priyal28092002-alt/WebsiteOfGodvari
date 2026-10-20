package com.example.String;

import java.util.*;

/**
 * CORE CONCEPT: Sorting and Comparison for Strings
 * 
 * Use when: Lexicographic ordering, comparing strings, sorting words
 * Time Complexity: O(n log n) for sorting, O(n) for comparison
 * 
 * Pattern Recognition:
 * - Sort strings lexicographically
 * - Compare strings (alphabetically)
 * - Find kth lexicographical string
 * - Custom sorting
 * - String as numbers comparison
 * 
 * Key Concepts:
 * 1. Lexicographic order (dictionary order)
 * 2. compareTo() returns negative/zero/positive
 * 3. Custom Comparators
 * 4. Sorting by length, frequency, etc.
 * 
 * Applications:
 * - Sort array of strings
 * - Find lexicographically smallest/largest
 * - Sort by length
 * - Sort by frequency
 */
public class StringSortingTechnique {
    
    public static void main(String[] args) {
        String[] words = {"banana", "apple", "cherry", "date", "elderberry"};
        
        System.out.println("Original: " + Arrays.toString(words));
        
        // Lexicographic sorting
        String[] sorted1 = words.clone();
        sortLexicographically(sorted1);
        System.out.println("Lexicographic: " + Arrays.toString(sorted1));
        
        // Sort by length
        String[] sorted2 = words.clone();
        sortByLength(sorted2);
        System.out.println("By length: " + Arrays.toString(sorted2));
        
        // Sort by reverse order
        String[] sorted3 = words.clone();
        sortReverse(sorted3);
        System.out.println("Reverse: " + Arrays.toString(sorted3));
        
        // Comparison examples
        System.out.println("\n\nComparison:");
        System.out.println("'apple' vs 'banana': " + "apple".compareTo("banana"));
        System.out.println("'banana' vs 'apple': " + "banana".compareTo("apple"));
        System.out.println("'apple' vs 'apple': " + "apple".compareTo("apple"));
        
        // Find smallest and largest
        System.out.println("\n\nSmallest (lex): " + findSmallest(words));
        System.out.println("Largest (lex): " + findLargest(words));
        
        // Sort by character frequency
        String s = "tree";
        System.out.println("\n\nSort characters by frequency in '" + s + "': " 
                          + sortByFrequency(s));
        
        // Custom sorts
        String[] nums = {"3", "30", "34", "5", "9"};
        System.out.println("\n\nNumbers: " + Arrays.toString(nums));
        System.out.println("Largest number: " + largestNumber(nums));
    }
    
    /**
     * Sort strings lexicographically (dictionary order)
     * Concept: Natural ordering of strings
     */
    public static void sortLexicographically(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr);
    }
    
    /**
     * Sort strings by length (shortest first)
     * Concept: Custom comparator based on length
     */
    public static void sortByLength(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr, (s1, s2) -> s1.length() - s2.length());
    }
    
    /**
     * Sort strings in reverse lexicographic order
     */
    public static void sortReverse(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr, Collections.reverseOrder());
    }
    
    /**
     * Sort by length, then lexicographically for same length
     */
    public static void sortByLengthThenLex(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr, (s1, s2) -> {
            if (s1.length() != s2.length()) {
                return s1.length() - s2.length();
            }
            return s1.compareTo(s2);
        });
    }
    
    /**
     * Find lexicographically smallest string
     */
    public static String findSmallest(String[] arr) {
        if (arr == null || arr.length == 0) return null;
        
        String smallest = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(smallest) < 0) {
                smallest = arr[i];
            }
        }
        
        return smallest;
    }
    
    /**
     * Find lexicographically largest string
     */
    public static String findLargest(String[] arr) {
        if (arr == null || arr.length == 0) return null;
        
        String largest = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(largest) > 0) {
                largest = arr[i];
            }
        }
        
        return largest;
    }
    
    /**
     * Sort string characters by frequency (high to low)
     * Example: "tree" -> "eert" (e appears 2 times)
     */
    public static String sortByFrequency(String s) {
        if (s == null || s.length() <= 1) return s;
        
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        List<Character> chars = new ArrayList<>(freq.keySet());
        chars.sort((a, b) -> {
            int freqCompare = freq.get(b) - freq.get(a);
            if (freqCompare != 0) return freqCompare;
            return a - b; // Tie-breaker: lexicographic
        });
        
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            int count = freq.get(c);
            for (int i = 0; i < count; i++) {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Sort characters in string alphabetically
     */
    public static String sortCharacters(String s) {
        if (s == null || s.length() <= 1) return s;
        
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
    
    /**
     * Largest number from array of number strings
     * Concept: Custom comparator - compare concatenations
     * Example: ["3", "30", "34"] -> "34330"
     */
    public static String largestNumber(String[] nums) {
        if (nums == null || nums.length == 0) return "";
        
        Arrays.sort(nums, (a, b) -> (b + a).compareTo(a + b));
        
        if (nums[0].equals("0")) return "0";
        
        StringBuilder result = new StringBuilder();
        for (String num : nums) {
            result.append(num);
        }
        
        return result.toString();
    }
    
    /**
     * Compare two version strings
     * Example: "1.2" vs "1.10" -> -1 (1.2 is older)
     * Returns: negative if v1 < v2, zero if equal, positive if v1 > v2
     */
    public static int compareVersion(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");
        
        int maxLen = Math.max(parts1.length, parts2.length);
        
        for (int i = 0; i < maxLen; i++) {
            int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            
            if (num1 != num2) {
                return num1 - num2;
            }
        }
        
        return 0;
    }
    
    /**
     * Find kth lexicographically smallest string
     */
    public static String kthSmallest(String[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) return null;
        
        Arrays.sort(arr);
        return arr[k - 1];
    }
    
    /**
     * Sort strings ignoring case
     */
    public static void sortIgnoreCase(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER);
    }
    
    /**
     * Custom sort: vowels before consonants, then alphabetically
     */
    public static void sortVowelsFirst(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr, (s1, s2) -> {
            boolean v1 = isVowel(s1.charAt(0));
            boolean v2 = isVowel(s2.charAt(0));
            
            if (v1 && !v2) return -1;
            if (!v1 && v2) return 1;
            return s1.compareTo(s2);
        });
    }
    
    private static boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
    
    /**
     * Sort by number of vowels (descending)
     */
    public static void sortByVowelCount(String[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        Arrays.sort(arr, (s1, s2) -> countVowels(s2) - countVowels(s1));
    }
    
    private static int countVowels(String s) {
        int count = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            if (isVowel(c)) count++;
        }
        return count;
    }
    
    /**
     * Check if array is sorted lexicographically
     */
    public static boolean isSorted(String[] arr) {
        if (arr == null || arr.length <= 1) return true;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[i - 1]) < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Merge two sorted string arrays
     */
    public static String[] mergeSortedArrays(String[] arr1, String[] arr2) {
        if (arr1 == null) return arr2;
        if (arr2 == null) return arr1;
        
        String[] merged = new String[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i].compareTo(arr2[j]) <= 0) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        
        while (i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        
        while (j < arr2.length) {
            merged[k++] = arr2[j++];
        }
        
        return merged;
    }
}
