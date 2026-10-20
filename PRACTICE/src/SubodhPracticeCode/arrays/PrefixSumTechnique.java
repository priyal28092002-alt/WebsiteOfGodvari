package com.example.arrays;

import java.util.Arrays;

/**
 * CORE CONCEPT: Prefix Sum / Cumulative Sum
 * 
 * Use when: Multiple range sum queries, subarray sum problems
 * Time Complexity: O(n) preprocessing, O(1) per query
 * 
 * Pattern Recognition:
 * - Finding sum of elements in range [L, R] multiple times
 * - Subarray sum equals K
 * - Equilibrium index (left sum = right sum)
 * - Maximum subarray sum (with variations)
 * 
 * Applications:
 * - Range sum queries
 * - Count subarrays with given sum
 * - Find equilibrium point
 * - Product of array except self
 */
public class PrefixSumTechnique {
    
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("Array: " + Arrays.toString(arr));
        
        // Build prefix sum array
        int[] prefix = buildPrefixSum(arr);
        System.out.println("\nPrefix sum array: " + Arrays.toString(prefix));
        
        // Range sum queries
        System.out.println("\nRange Sum Queries:");
        System.out.println("Sum [2, 5]: " + rangeSum(prefix, 2, 5));
        System.out.println("Sum [0, 3]: " + rangeSum(prefix, 0, 3));
        System.out.println("Sum [7, 9]: " + rangeSum(prefix, 7, 9));
        
        // Equilibrium index
        int[] eqArr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println("\n\nArray: " + Arrays.toString(eqArr));
        int eqIndex = findEquilibriumIndex(eqArr);
        System.out.println("Equilibrium index: " + eqIndex);
        
        // Subarray sum equals K
        int[] sumArr = {1, 2, 3, 7, 5};
        int k = 12;
        System.out.println("\n\nArray: " + Arrays.toString(sumArr));
        System.out.println("Subarray with sum " + k + " exists: " + 
                          hasSubarrayWithSum(sumArr, k));
        
        // Maximum subarray sum (Kadane's algorithm)
        int[] kadaneArr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("\n\nArray: " + Arrays.toString(kadaneArr));
        System.out.println("Maximum subarray sum: " + maxSubarraySum(kadaneArr));
        
        // Product except self
        int[] prodArr = {1, 2, 3, 4};
        System.out.println("\n\nArray: " + Arrays.toString(prodArr));
        System.out.println("Product except self: " + 
                          Arrays.toString(productExceptSelf(prodArr)));
    }
    
    /**
     * Build prefix sum array
     * prefix[i] = sum of elements from index 0 to i
     */
    public static int[] buildPrefixSum(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        
        int[] prefix = new int[arr.length];
        prefix[0] = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            prefix[i] = prefix[i - 1] + arr[i];
        }
        
        return prefix;
    }
    
    /**
     * Range sum query using prefix sum
     * Sum[L, R] = prefix[R] - prefix[L-1]
     */
    public static int rangeSum(int[] prefix, int left, int right) {
        if (prefix == null || left < 0 || right >= prefix.length) {
            return 0;
        }
        
        if (left == 0) {
            return prefix[right];
        }
        
        return prefix[right] - prefix[left - 1];
    }
    
    /**
     * Find equilibrium index where left sum = right sum
     * Concept: leftSum = prefix[i-1], rightSum = total - prefix[i]
     */
    public static int findEquilibriumIndex(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }
        
        int leftSum = 0;
        
        for (int i = 0; i < arr.length; i++) {
            // rightSum = totalSum - leftSum - arr[i]
            int rightSum = totalSum - leftSum - arr[i];
            
            if (leftSum == rightSum) {
                return i;
            }
            
            leftSum += arr[i];
        }
        
        return -1;
    }
    
    /**
     * Check if subarray with given sum exists
     * Concept: If prefix[j] - prefix[i] = k, then subarray exists
     * Using HashSet to check if (currentSum - k) exists
     */
    public static boolean hasSubarrayWithSum(int[] arr, int target) {
        if (arr == null || arr.length == 0) return false;
        
        java.util.Set<Integer> prefixSums = new java.util.HashSet<>();
        prefixSums.add(0); // For subarray starting from index 0
        
        int currentSum = 0;
        
        for (int num : arr) {
            currentSum += num;
            
            // If (currentSum - target) exists in set,
            // then there's a subarray with sum = target
            if (prefixSums.contains(currentSum - target)) {
                return true;
            }
            
            prefixSums.add(currentSum);
        }
        
        return false;
    }
    
    /**
     * Maximum Subarray Sum - Kadane's Algorithm
     * Concept: At each position, decide to extend subarray or start new
     * currentSum = max(arr[i], currentSum + arr[i])
     */
    public static int maxSubarraySum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        int maxSum = arr[0];
        int currentSum = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            // Either extend existing subarray or start new from current element
            currentSum = Math.max(arr[i], currentSum + arr[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    /**
     * Product of Array Except Self (without division)
     * Concept: Use prefix product from left and suffix product from right
     * result[i] = leftProduct[i-1] * rightProduct[i+1]
     */
    public static int[] productExceptSelf(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        
        int n = arr.length;
        int[] result = new int[n];
        
        // Build left products
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * arr[i - 1];
        }
        
        // Build right products and multiply with left
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] = result[i] * rightProduct;
            rightProduct *= arr[i];
        }
        
        return result;
    }
    
    /**
     * Count subarrays with sum equals K
     */
    public static int countSubarraysWithSum(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;
        
        java.util.Map<Integer, Integer> prefixSumCount = new java.util.HashMap<>();
        prefixSumCount.put(0, 1);
        
        int currentSum = 0;
        int count = 0;
        
        for (int num : arr) {
            currentSum += num;
            
            // If (currentSum - k) exists, those subarrays sum to k
            count += prefixSumCount.getOrDefault(currentSum - k, 0);
            
            prefixSumCount.put(currentSum, 
                              prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
}
