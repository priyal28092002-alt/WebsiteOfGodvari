
package com.example.arrays;

import java.util.Arrays;

/**
 * CORE CONCEPT: Array Rotation and Cyclic Operations
 * 
 * Use when: Shifting elements, rotating arrays, cyclic patterns
 * Time Complexity: O(n) with O(1) space using reversal algorithm
 * 
 * Pattern Recognition:
 * - Rotate array left/right by k positions
 * - Find rotation count in sorted rotated array
 * - Search in rotated sorted array
 * - Maximum circular subarray sum
 * 
 * Key Technique: Reversal Algorithm
 * To rotate right by k:
 * 1. Reverse entire array
 * 2. Reverse first k elements
 * 3. Reverse remaining elements
 * 
 * Applications:
 * - Array rotation problems
 * - Circular array operations
 * - Finding patterns in rotated data
 */
public class ArrayRotationTechnique {
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("Original array: " + Arrays.toString(arr));
        
        // Left rotation
        int[] leftRotated = arr.clone();
        rotateLeft(leftRotated, 2);
        System.out.println("Left rotate by 2: " + Arrays.toString(leftRotated));
        
        // Right rotation
        int[] rightRotated = arr.clone();
        rotateRight(rightRotated, 2);
        System.out.println("Right rotate by 2: " + Arrays.toString(rightRotated));
        
        // Search in rotated sorted array
        int[] rotatedSorted = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("\n\nRotated sorted array: " + Arrays.toString(rotatedSorted));
        System.out.println("Search for 0: index = " + searchInRotatedArray(rotatedSorted, 0));
        System.out.println("Search for 6: index = " + searchInRotatedArray(rotatedSorted, 6));
        
        // Find rotation count
        System.out.println("Rotation count: " + findRotationCount(rotatedSorted));
        
        // Maximum circular subarray sum
        int[] circularArr = {8, -8, 9, -9, 10, -11, 12};
        System.out.println("\n\nCircular array: " + Arrays.toString(circularArr));
        System.out.println("Max circular subarray sum: " + maxCircularSum(circularArr));
        
        // Rearrange array
        int[] rearrange = {-1, 2, -3, 4, 5, 6, -7, 8, 9};
        System.out.println("\n\nArray: " + Arrays.toString(rearrange));
        rearrangeAlternate(rearrange);
        System.out.println("Rearranged (pos-neg alternating): " + Arrays.toString(rearrange));
    }
    
    /**
     * Rotate array left by k positions using reversal algorithm
     * Example: [1,2,3,4,5] left rotate by 2 -> [3,4,5,1,2]
     * 
     * Steps:
     * 1. Reverse first k elements: [2,1,3,4,5]
     * 2. Reverse remaining: [2,1,5,4,3]
     * 3. Reverse entire: [3,4,5,1,2]
     */
    public static void rotateLeft(int[] arr, int k) {
        if (arr == null || arr.length <= 1 || k <= 0) return;
        
        k = k % arr.length; // Handle k > length
        if (k == 0) return;
        
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
        reverse(arr, 0, arr.length - 1);
    }
    
    /**
     * Rotate array right by k positions using reversal algorithm
     * Example: [1,2,3,4,5] right rotate by 2 -> [4,5,1,2,3]
     * 
     * Steps:
     * 1. Reverse entire array: [5,4,3,2,1]
     * 2. Reverse first k elements: [4,5,3,2,1]
     * 3. Reverse remaining: [4,5,1,2,3]
     */
    public static void rotateRight(int[] arr, int k) {
        if (arr == null || arr.length <= 1 || k <= 0) return;
        
        k = k % arr.length;
        if (k == 0) return;
        
        reverse(arr, 0, arr.length - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
    }
    
    /**
     * Search in rotated sorted array
     * Concept: One half is always sorted, determine which half target lies in
     */
    public static int searchInRotatedArray(int[] arr, int target) {
        if (arr == null || arr.length == 0) return -1;
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (arr[left] <= arr[mid]) {
                // Left half is sorted
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Find rotation count in sorted rotated array
     * Concept: Minimum element index = rotation count
     */
    public static int findRotationCount(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int left = 0;
        int right = arr.length - 1;
        
        while (left < right) {
            // Already sorted
            if (arr[left] < arr[right]) {
                return left;
            }
            
            int mid = left + (right - left) / 2;
            int next = (mid + 1) % arr.length;
            int prev = (mid - 1 + arr.length) % arr.length;
            
            // Check if mid is minimum
            if (arr[mid] <= arr[next] && arr[mid] <= arr[prev]) {
                return mid;
            }
            
            // Decide which half to search
            if (arr[mid] <= arr[right]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    /**
     * Maximum circular subarray sum
     * Concept: Max circular sum = max(Kadane's max, Total - Kadane's min)
     * 
     * Two cases:
     * 1. Max subarray doesn't wrap around -> Kadane's algorithm
     * 2. Max subarray wraps around -> Total - minimum subarray
     */
    public static int maxCircularSum(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        // Case 1: Maximum subarray sum (no wrapping)
        int maxKadane = kadaneMax(arr);
        
        // Case 2: Maximum with wrapping = Total - minimum subarray
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }
        
        int minKadane = kadaneMin(arr);
        int maxWrap = totalSum - minKadane;
        
        // If all elements are negative, maxWrap will be 0, use maxKadane
        if (maxWrap == 0) {
            return maxKadane;
        }
        
        return Math.max(maxKadane, maxWrap);
    }
    
    private static int kadaneMax(int[] arr) {
        int maxSum = arr[0];
        int currentSum = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            currentSum = Math.max(arr[i], currentSum + arr[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    private static int kadaneMin(int[] arr) {
        int minSum = arr[0];
        int currentSum = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            currentSum = Math.min(arr[i], currentSum + arr[i]);
            minSum = Math.min(minSum, currentSum);
        }
        
        return minSum;
    }
    
    /**
     * Rearrange positive and negative numbers alternately
     * Maintains relative order
     */
    public static void rearrangeAlternate(int[] arr) {
        if (arr == null || arr.length < 2) return;
        
        int n = arr.length;
        int[] temp = new int[n];
        int pos = 0, neg = 1;
        
        for (int num : arr) {
            if (num >= 0 && pos < n) {
                temp[pos] = num;
                pos += 2;
            } else if (num < 0 && neg < n) {
                temp[neg] = num;
                neg += 2;
            }
        }
        
        System.arraycopy(temp, 0, arr, 0, n);
    }
    
    /**
     * Reverse array elements in range [left, right]
     */
    private static void reverse(int[] arr, int left, int right) {
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
