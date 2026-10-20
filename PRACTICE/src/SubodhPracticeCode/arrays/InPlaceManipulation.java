package com.example.arrays;

import java.util.Arrays;

/**
 * CORE CONCEPT: In-Place Array Manipulation
 * 
 * Use when: Space constraint O(1), rearranging without extra array
 * Techniques: Swapping, index manipulation, sign marking
 * 
 * Pattern Recognition:
 * - Partition/segregate elements (0s & 1s, even & odd)
 * - Move elements to end (zeros, negatives)
 * - In-place reversal/rotation
 * - Cyclic sort pattern (for array with elements 1 to n)
 * 
 * Key Insight: Use array indices/values themselves as metadata
 * 
 * Applications:
 * - Move zeros to end
 * - Segregate positive and negative
 * - Rearrange array by index
 * - Find duplicates without extra space
 * - Cyclic sort for missing numbers
 */
public class InPlaceManipulation {
    
    public static void main(String[] args) {
        // Move zeros to end
        int[] arr1 = {0, 1, 0, 3, 12, 0, 5};
        System.out.println("Original: " + Arrays.toString(arr1));
        moveZerosToEnd(arr1);
        System.out.println("After moving zeros: " + Arrays.toString(arr1));
        
        // Segregate positive and negative
        int[] arr2 = {-12, 11, -13, -5, 6, -7, 5, -3, -6};
        System.out.println("\n\nOriginal: " + Arrays.toString(arr2));
        segregatePositiveNegative(arr2);
        System.out.println("After segregation: " + Arrays.toString(arr2));
        
        // Rearrange array by sign alternately
        int[] arr3 = {-5, -2, 5, 2, 4, 7, 1, 8, 0, -8};
        System.out.println("\n\nOriginal: " + Arrays.toString(arr3));
        rearrangeAlternatePositiveNegative(arr3);
        System.out.println("After alternating: " + Arrays.toString(arr3));
        
        // Find duplicates using sign marking
        int[] arr4 = {1, 2, 3, 1, 3, 6, 6};
        System.out.println("\n\nArray (1 to n): " + Arrays.toString(arr4));
        System.out.println("Duplicates: " + findDuplicatesInPlace(arr4));
        
        // Cyclic sort - find missing number
        int[] arr5 = {3, 0, 1};
        System.out.println("\n\nArray: " + Arrays.toString(arr5));
        System.out.println("Missing number: " + findMissingNumberCyclicSort(arr5));
        
        // Rearrange by index
        int[] arr6 = {-1, -1, 6, 1, 9, 3, 2, -1, 4, -1};
        System.out.println("\n\nArray: " + Arrays.toString(arr6));
        rearrangeByIndex(arr6);
        System.out.println("Rearranged by index: " + Arrays.toString(arr6));
    }
    
    /**
     * Move all zeros to end while maintaining relative order
     * Concept: Two pointer - one for non-zero position, one for scanning
     */
    public static void moveZerosToEnd(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        int nonZeroPos = 0; // Position for next non-zero element
        
        // Move all non-zero elements to front
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[nonZeroPos] = arr[i];
                nonZeroPos++;
            }
        }
        
        // Fill remaining with zeros
        while (nonZeroPos < arr.length) {
            arr[nonZeroPos] = 0;
            nonZeroPos++;
        }
    }
    
    /**
     * Alternative: Swap-based approach (may not maintain order)
     */
    public static void moveZerosToEndSwap(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            if (arr[left] == 0 && arr[right] != 0) {
                swap(arr, left, right);
                left++;
                right--;
            } else if (arr[left] != 0) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    /**
     * Segregate positive and negative numbers
     * Concept: Partition like quicksort
     */
    public static void segregatePositiveNegative(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            if (arr[left] < 0) {
                left++;
            } else if (arr[right] >= 0) {
                right--;
            } else {
                swap(arr, left, right);
                left++;
                right--;
            }
        }
    }
    
    /**
     * Rearrange array with positive and negative alternately
     * Maintains relative order
     */
    public static void rearrangeAlternatePositiveNegative(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        // First segregate
        segregatePositiveNegative(arr);
        
        // Find partition point
        int pos = 0;
        while (pos < arr.length && arr[pos] < 0) {
            pos++;
        }
        
        // Merge alternately
        int neg = 0;
        
        while (neg < pos && pos < arr.length) {
            swap(arr, neg, pos);
            neg += 2;
            pos++;
        }
    }
    
    /**
     * Find duplicates in array containing elements 1 to n
     * Concept: Use array indices - mark visited by making negative
     * Space: O(1), restores original array
     */
    public static java.util.List<Integer> findDuplicatesInPlace(int[] arr) {
        java.util.List<Integer> duplicates = new java.util.ArrayList<>();
        
        for (int i = 0; i < arr.length; i++) {
            int index = Math.abs(arr[i]) - 1;
            
            if (index < arr.length) {
                if (arr[index] < 0) {
                    // Already visited - duplicate
                    duplicates.add(index + 1);
                } else {
                    // Mark as visited
                    arr[index] = -arr[index];
                }
            }
        }
        
        // Restore array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Math.abs(arr[i]);
        }
        
        return duplicates;
    }
    
    /**
     * Cyclic Sort: Find missing number in 0 to n
     * Concept: Place each element at its correct index
     * arr[i] should be at index arr[i]
     */
    public static int findMissingNumberCyclicSort(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int n = arr.length;
        
        // Cyclic sort - place each number at correct position
        int i = 0;
        while (i < n) {
            int correctIndex = arr[i];
            
            if (arr[i] < n && arr[i] != arr[correctIndex]) {
                swap(arr, i, correctIndex);
            } else {
                i++;
            }
        }
        
        // Find first position where index != value
        for (i = 0; i < n; i++) {
            if (arr[i] != i) {
                return i;
            }
        }
        
        return n;
    }
    
    /**
     * Find all missing numbers in 1 to n using cyclic sort
     */
    public static java.util.List<Integer> findAllMissingNumbers(int[] arr) {
        java.util.List<Integer> missing = new java.util.ArrayList<>();
        if (arr == null || arr.length == 0) return missing;
        
        int n = arr.length;
        
        // Cyclic sort
        int i = 0;
        while (i < n) {
            int correctIndex = arr[i] - 1;
            
            if (arr[i] > 0 && arr[i] <= n && arr[i] != arr[correctIndex]) {
                swap(arr, i, correctIndex);
            } else {
                i++;
            }
        }
        
        // Find missing numbers
        for (i = 0; i < n; i++) {
            if (arr[i] != i + 1) {
                missing.add(i + 1);
            }
        }
        
        return missing;
    }
    
    /**
     * Rearrange array such that arr[i] = i
     * If i is not present, set arr[i] = -1
     * Array contains -1 or values from 0 to n-1
     */
    public static void rearrangeByIndex(int[] arr) {
        if (arr == null || arr.length == 0) return;
        
        for (int i = 0; i < arr.length; i++) {
            // Keep swapping until correct element is at position i
            while (arr[i] != -1 && arr[i] != i) {
                int correctIndex = arr[i];
                
                if (correctIndex < 0 || correctIndex >= arr.length) {
                    break;
                }
                
                // Swap with correct position
                swap(arr, i, correctIndex);
            }
        }
        
        // Set positions without matching index to -1
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i) {
                arr[i] = -1;
            }
        }
    }
    
    /**
     * Rearrange array: arr[i] becomes arr[arr[i]]
     * All elements are in range 0 to n-1
     * Concept: Encode new value with old using multiplication
     */
    public static void rearrangeWithFormula(int[] arr) {
        if (arr == null || arr.length == 0) return;
        
        int n = arr.length;
        
        // Encode both old and new value
        for (int i = 0; i < n; i++) {
            arr[i] = arr[i] + (arr[arr[i]] % n) * n;
        }
        
        // Decode to get new values
        for (int i = 0; i < n; i++) {
            arr[i] = arr[i] / n;
        }
    }
    
    /**
     * Reverse array in-place
     */
    public static void reverse(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
