package com.example.arrays;

import java.util.Arrays;

/**
 * CORE CONCEPT: Two Pointer Technique
 * 
 * Use when: Searching pairs/triplets, partitioning, reversing, merging
 * Time Complexity: O(n) or O(n²) instead of O(n²) or O(n³)
 * 
 * Pattern Recognition:
 * - Array is sorted or can be sorted
 * - Finding pairs with specific sum/difference
 * - Partitioning based on condition
 * - Comparing elements from both ends
 * 
 * Applications:
 * - Two sum in sorted array
 * - Remove duplicates from sorted array
 * - Container with most water
 * - Three sum problem
 * - Dutch National Flag (sort 0s, 1s, 2s)
 */
public class TwoPointerTechnique {
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 10;
        
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("\nFinding pair with sum = " + target);
        int[] pair = findPairWithSum(arr, target);
        if (pair != null) {
            System.out.println("Pair found: [" + pair[0] + ", " + pair[1] + "]");
        }
        
        int[] unsorted = {3, 1, 4, 1, 5, 9, 2, 6};
        System.out.println("\n\nUnsorted: " + Arrays.toString(unsorted));
        System.out.println("Finding pair with sum = " + target);
        pair = findPairWithSumUnsorted(unsorted, target);
        if (pair != null) {
            System.out.println("Pair found: [" + pair[0] + ", " + pair[1] + "]");
        }
        
        int[] duplicates = {1, 1, 2, 2, 2, 3, 4, 4, 5};
        System.out.println("\n\nWith duplicates: " + Arrays.toString(duplicates));
        int newLength = removeDuplicates(duplicates);
        System.out.println("After removing duplicates: " + 
                          Arrays.toString(Arrays.copyOf(duplicates, newLength)));
        
        int[] dutch = {2, 0, 1, 2, 0, 1, 0, 2, 1};
        System.out.println("\n\nDutch National Flag (0s, 1s, 2s)");
        System.out.println("Before: " + Arrays.toString(dutch));
        sortColors(dutch);
        System.out.println("After:  " + Arrays.toString(dutch));
        
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("\n\nContainer With Most Water");
        System.out.println("Heights: " + Arrays.toString(heights));
        System.out.println("Max area: " + maxArea(heights));
    }
    
    /**
     * Two Sum in Sorted Array
     * Key: Move pointers based on sum comparison
     */
    public static int[] findPairWithSum(int[] arr, int target) {
        if (arr == null || arr.length < 2) return null;
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            int sum = arr[left] + arr[right];
            
            if (sum == target) {
                return new int[]{arr[left], arr[right]};
            } else if (sum < target) {
                left++;  // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }
        
        return null;
    }
    
    /**
     * Two Sum in Unsorted Array (requires sorting or HashSet)
     * This shows sorting approach
     */
    public static int[] findPairWithSumUnsorted(int[] arr, int target) {
        if (arr == null || arr.length < 2) return null;
        
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        
        return findPairWithSum(sorted, target);
    }
    
    /**
     * Remove Duplicates from Sorted Array (in-place)
     * Key: Slow pointer tracks unique position, fast pointer scans
     */
    public static int removeDuplicates(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int slow = 0; // Position for next unique element
        
        for (int fast = 1; fast < arr.length; fast++) {
            if (arr[fast] != arr[slow]) {
                slow++;
                arr[slow] = arr[fast];
            }
        }
        
        return slow + 1; // Length of unique elements
    }
    
    /**
     * Dutch National Flag: Sort 0s, 1s, 2s
     * Key: Three pointers - low (0s), mid (1s), high (2s)
     * Concept: Partition array into three regions
     */
    public static void sortColors(int[] arr) {
        if (arr == null || arr.length < 2) return;
        
        int low = 0;    // End of 0s region
        int mid = 0;    // Current element
        int high = arr.length - 1; // Start of 2s region
        
        while (mid <= high) {
            if (arr[mid] == 0) {
                swap(arr, low, mid);
                low++;
                mid++;
            } else if (arr[mid] == 1) {
                mid++;
            } else { // arr[mid] == 2
                swap(arr, mid, high);
                high--;
                // Don't increment mid - need to check swapped element
            }
        }
    }
    
    /**
     * Container With Most Water
     * Key: Start from both ends, move pointer with smaller height
     * Concept: Area = min(height[left], height[right]) * (right - left)
     */
    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;
        
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int area = width * minHeight;
            maxArea = Math.max(maxArea, area);
            
            // Move pointer with smaller height (bottleneck)
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Reverse array using two pointers
     */
    public static void reverse(int[] arr) {
        if (arr == null || arr.length < 2) return;
        
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
