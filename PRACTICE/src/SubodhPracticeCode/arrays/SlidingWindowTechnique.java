package com.example.arrays;

/**
 * CORE CONCEPT: Sliding Window Technique
 * 
 * Use when: Finding subarray with specific properties (max/min sum, k-elements, etc.)
 * Time Complexity: O(n) instead of O(n²) with nested loops
 * 
 * Pattern Recognition:
 * - Consecutive elements needed
 * - Fixed or variable window size
 * - Optimization problems on subarrays
 * 
 * Applications:
 * - Maximum sum of k consecutive elements
 * - Smallest subarray with sum >= target
 * - Longest substring with k unique characters
 * - Average of all subarrays of size k
 */
public class SlidingWindowTechnique {
    
    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 1, 3, 2};
        int k = 3;
        
        System.out.println("Array: " + java.util.Arrays.toString(arr));
        System.out.println("\nFixed Window Size: " + k);
        System.out.println("Max sum of " + k + " consecutive: " + maxSumFixedWindow(arr, k));
        System.out.println("Min sum of " + k + " consecutive: " + minSumFixedWindow(arr, k));
        
        int target = 7;
        System.out.println("\nVariable Window:");
        System.out.println("Smallest subarray with sum >= " + target + ": " + 
                          smallestSubarrayWithSum(arr, target));
        
        System.out.println("\nLongest subarray with sum <= " + target + ": " + 
                          longestSubarrayWithSum(arr, target));
    }
    
    /**
     * Fixed Window: Maximum sum of k consecutive elements
     * Key: Slide window by removing first element and adding next
     */
    public static int maxSumFixedWindow(int[] arr, int k) {
        if (arr == null || arr.length < k) return -1;
        
        // Calculate sum of first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        
        int maxSum = windowSum;
        
        // Slide window: remove arr[i-k], add arr[i]
        for (int i = k; i < arr.length; i++) {
            windowSum = windowSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    /**
     * Fixed Window: Minimum sum of k consecutive elements
     */
    public static int minSumFixedWindow(int[] arr, int k) {
        if (arr == null || arr.length < k) return -1;
        
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        
        int minSum = windowSum;
        
        for (int i = k; i < arr.length; i++) {
            windowSum = windowSum - arr[i - k] + arr[i];
            minSum = Math.min(minSum, windowSum);
        }
        
        return minSum;
    }
    
    /**
     * Variable Window: Smallest subarray with sum >= target
     * Technique: Expand right, shrink left when condition met
     */
    public static int smallestSubarrayWithSum(int[] arr, int target) {
        if (arr == null || arr.length == 0) return 0;
        
        int minLength = Integer.MAX_VALUE;
        int windowSum = 0;
        int left = 0;
        
        for (int right = 0; right < arr.length; right++) {
            windowSum += arr[right];
            
            // Shrink window from left while condition is met
            while (windowSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                windowSum -= arr[left];
                left++;
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Variable Window: Longest subarray with sum <= target
     */
    public static int longestSubarrayWithSum(int[] arr, int target) {
        if (arr == null || arr.length == 0) return 0;
        
        int maxLength = 0;
        int windowSum = 0;
        int left = 0;
        
        for (int right = 0; right < arr.length; right++) {
            windowSum += arr[right];
            
            // Shrink if sum exceeds target
            while (windowSum > target && left <= right) {
                windowSum -= arr[left];
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
