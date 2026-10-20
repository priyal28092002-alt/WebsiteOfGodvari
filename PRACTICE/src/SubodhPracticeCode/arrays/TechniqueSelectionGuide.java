package com.example.arrays;

/**
 * TECHNIQUE SELECTION GUIDE
 * 
 * Use this as a decision tree to quickly identify which technique to apply
 * to solve array problems in coding interviews like TCS NQT Prime.
 * 
 * =============================================================================
 * PROBLEM PATTERN RECOGNITION GUIDE
 * =============================================================================
 * 
 * 1. SLIDING WINDOW (SlidingWindowTechnique.java)
 *    When to use:
 *    - "Subarray" or "consecutive elements" mentioned
 *    - Find max/min/average of k consecutive elements
 *    - Longest/smallest subarray with condition
 *    - Words: "contiguous", "consecutive", "window"
 *    
 *    Examples:
 *    - Max sum of k consecutive elements
 *    - Longest subarray with sum <= k
 *    - Smallest subarray with sum >= k
 *    - Find all anagrams in string
 * 
 * 
 * 2. TWO POINTER (TwoPointerTechnique.java)
 *    When to use:
 *    - Array is sorted or can be sorted
 *    - Finding pairs/triplets with sum/difference
 *    - Partitioning array
 *    - Comparing from both ends
 *    - Words: "pair", "two elements", "sorted"
 *    
 *    Examples:
 *    - Two sum in sorted array
 *    - Three sum problem
 *    - Remove duplicates from sorted array
 *    - Container with most water
 *    - Dutch National Flag (sort 0s, 1s, 2s)
 * 
 * 
 * 3. PREFIX SUM / CUMULATIVE SUM (PrefixSumTechnique.java)
 *    When to use:
 *    - Multiple range queries for sum
 *    - Subarray sum equals k
 *    - Finding equilibrium point
 *    - Words: "range sum", "subarray sum", "left sum = right sum"
 *    
 *    Examples:
 *    - Range sum queries
 *    - Subarray with given sum
 *    - Equilibrium index
 *    - Maximum subarray sum (Kadane's)
 *    - Product except self
 * 
 * 
 * 4. ARRAY ROTATION (ArrayRotationTechnique.java)
 *    When to use:
 *    - Rotating/shifting array elements
 *    - Circular array problems
 *    - Sorted rotated array
 *    - Words: "rotate", "circular", "cyclic"
 *    
 *    Examples:
 *    - Rotate array left/right by k
 *    - Search in rotated sorted array
 *    - Find rotation count
 *    - Maximum circular subarray sum
 * 
 * 
 * 5. FREQUENCY COUNTING / HASHING (FrequencyCountingTechnique.java)
 *    When to use:
 *    - Counting occurrences
 *    - Finding duplicates/missing
 *    - First non-repeating element
 *    - Words: "frequency", "duplicate", "unique", "count"
 *    
 *    Examples:
 *    - Find duplicates
 *    - First non-repeating element
 *    - Majority element (> n/2 times)
 *    - Top k frequent elements
 *    - Subarray with sum 0
 * 
 * 
 * 6. SORTING-BASED (SortingBasedTechnique.java)
 *    When to use:
 *    - Problem becomes easier after sorting
 *    - Finding pairs/triplets
 *    - Interval/meeting problems
 *    - Words: "merge", "intervals", "meetings", "closest"
 *    
 *    Examples:
 *    - Three sum problem
 *    - Merge overlapping intervals
 *    - Meeting rooms
 *    - Minimum platforms required
 *    - Closest pair to target sum
 * 
 * 
 * 7. IN-PLACE MANIPULATION (InPlaceManipulation.java)
 *    When to use:
 *    - Space constraint O(1)
 *    - Rearranging elements
 *    - Array contains elements 1 to n
 *    - Words: "in-place", "O(1) space", "rearrange"
 *    
 *    Examples:
 *    - Move zeros to end
 *    - Segregate positive and negative
 *    - Find duplicates without extra space
 *    - Cyclic sort for missing numbers
 *    - Rearrange by index
 * 
 * 
 * 8. STACK-BASED (StackBasedTechnique.java)
 *    When to use:
 *    - Next/Previous Greater/Smaller Element
 *    - Histogram/bar problems
 *    - Monotonic sequences
 *    - Words: "next greater", "previous smaller", "histogram", "span"
 *    
 *    Examples:
 *    - Next Greater Element (NGE)
 *    - Stock Span Problem
 *    - Largest Rectangle in Histogram
 *    - Rain Water Trapping
 *    - Celebrity Problem
 * 
 * 
 * 9. MATHEMATICAL / BITWISE (MathematicalTechnique.java)
 *    When to use:
 *    - Finding missing/duplicate using formulas
 *    - XOR properties
 *    - Bit manipulation
 *    - GCD/LCM problems
 *    - Words: "XOR", "bits", "power of 2", "single number"
 *    
 *    Examples:
 *    - Find missing number (sum or XOR)
 *    - Single number (others appear twice)
 *    - Two unique numbers (XOR technique)
 *    - Generate all subsets (bit masking)
 *    - GCD/LCM of array
 * 
 * 
 * =============================================================================
 * QUICK DECISION TREE
 * =============================================================================
 * 
 * START HERE:
 * 
 * Q: Does problem involve consecutive/contiguous elements?
 *    YES → Sliding Window
 *    NO  → Continue
 * 
 * Q: Is array sorted or can be sorted?
 *    YES → Q: Finding pairs/triplets?
 *          YES → Two Pointer or Sorting-Based
 *          NO  → Continue
 *    NO  → Continue
 * 
 * Q: Multiple range sum queries?
 *    YES → Prefix Sum
 *    NO  → Continue
 * 
 * Q: Involves rotation/circular array?
 *    YES → Array Rotation
 *    NO  → Continue
 * 
 * Q: Counting frequency/duplicates?
 *    YES → Frequency Counting
 *    NO  → Continue
 * 
 * Q: Need O(1) space and rearranging?
 *    YES → In-Place Manipulation
 *    NO  → Continue
 * 
 * Q: Finding next/previous greater/smaller?
 *    YES → Stack-Based
 *    NO  → Continue
 * 
 * Q: Can use mathematical formula or XOR?
 *    YES → Mathematical/Bitwise
 *    NO  → May need combination of techniques
 * 
 * 
 * =============================================================================
 * TIME COMPLEXITY CHEAT SHEET
 * =============================================================================
 * 
 * O(n)     - Sliding Window, Two Pointer, Prefix Sum, Hashing, Stack
 * O(n²)    - Three Sum (after sorting), Nested loops
 * O(n log n) - Sorting-based approaches
 * O(1) space - In-Place, Two Pointer, Mathematical
 * O(n) space - Hashing, Prefix arrays, Stack
 * 
 * 
 * =============================================================================
 * COMMON TCS NQT PROBLEM TYPES
 * =============================================================================
 * 
 * EASY:
 * - Find largest/smallest element → Linear scan O(n)
 * - Sum of array elements → Linear scan O(n)
 * - Reverse array → Two Pointer O(n)
 * - Find duplicate → Hashing O(n)
 * - Missing number → Mathematical O(n)
 * 
 * MEDIUM:
 * - Maximum subarray sum → Prefix Sum (Kadane's) O(n)
 * - Two sum → Two Pointer O(n)
 * - Next Greater Element → Stack O(n)
 * - Rotate array → Rotation Technique O(n)
 * - Move zeros → In-Place O(n)
 * 
 * HARD:
 * - Three sum → Sorting + Two Pointer O(n²)
 * - Largest Rectangle → Stack O(n)
 * - Merge intervals → Sorting O(n log n)
 * - Trapping rain water → Two Pointer O(n)
 * - Majority element → Moore's Voting O(n)
 * 
 * 
 * =============================================================================
 * PRACTICE STRATEGY
 * =============================================================================
 * 
 * 1. Learn one technique at a time
 * 2. Solve 5-10 problems using that technique
 * 3. Understand WHY the technique works
 * 4. Practice recognizing the pattern in problem statement
 * 5. Move to next technique
 * 6. Practice mixed problems to improve pattern recognition
 * 
 * Mastering these 9 core techniques will enable you to solve 90%+ of
 * array problems asked in TCS NQT Prime and similar coding rounds.
 */
public class TechniqueSelectionGuide {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("ARRAY PROBLEM SOLVING - TECHNIQUE SELECTION GUIDE");
        System.out.println("=".repeat(70));
        System.out.println();
        
        printTechniqueSummary();
        System.out.println();
        printExampleProblems();
    }
    
    private static void printTechniqueSummary() {
        System.out.println("9 CORE TECHNIQUES:");
        System.out.println("-".repeat(70));
        System.out.println("1. Sliding Window       → Consecutive elements, max/min of k");
        System.out.println("2. Two Pointer          → Sorted array, pairs, partitioning");
        System.out.println("3. Prefix Sum           → Range queries, subarray sum");
        System.out.println("4. Array Rotation       → Rotate, circular, sorted rotated");
        System.out.println("5. Frequency Counting   → Duplicates, missing, frequency");
        System.out.println("6. Sorting-Based        → Intervals, meetings, closest pairs");
        System.out.println("7. In-Place Manipulation → O(1) space, rearrange, move elements");
        System.out.println("8. Stack-Based          → Next greater, histogram, span");
        System.out.println("9. Mathematical/Bitwise → XOR, formulas, bit manipulation");
    }
    
    private static void printExampleProblems() {
        System.out.println("PROBLEM → TECHNIQUE MAPPING:");
        System.out.println("-".repeat(70));
        
        String[][] examples = {
            {"Max sum of k consecutive elements", "Sliding Window"},
            {"Two sum in sorted array", "Two Pointer"},
            {"Subarray sum equals k", "Prefix Sum"},
            {"Search in rotated sorted array", "Array Rotation"},
            {"Find all duplicates", "Frequency Counting"},
            {"Merge overlapping intervals", "Sorting-Based"},
            {"Move zeros to end", "In-Place Manipulation"},
            {"Next Greater Element", "Stack-Based"},
            {"Find missing number", "Mathematical"},
            {"Three sum problem", "Sorting + Two Pointer"},
            {"Largest rectangle in histogram", "Stack-Based"},
            {"Maximum subarray sum", "Prefix Sum (Kadane's)"},
            {"Dutch National Flag", "Two Pointer"},
            {"Majority element", "Frequency (Moore's Voting)"},
            {"Stock Span Problem", "Stack-Based"}
        };
        
        for (String[] example : examples) {
            System.out.printf("%-40s → %s%n", example[0], example[1]);
        }
    }
}
