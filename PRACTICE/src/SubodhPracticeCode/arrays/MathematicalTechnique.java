package com.example.arrays;

import java.util.Arrays;

/**
 * CORE CONCEPT: Mathematical and Bitwise Techniques
 * 
 * Use when: Finding patterns using math, XOR properties, bit manipulation
 * Time Complexity: Usually O(n), Space: O(1)
 * 
 * Pattern Recognition:
 * - Finding missing/duplicate using sum formula
 * - XOR properties for finding unique elements
 * - Bit manipulation for subset problems
 * - GCD/LCM for array operations
 * 
 * Key Properties:
 * - XOR: a ^ a = 0, a ^ 0 = a (useful for finding unique)
 * - Sum: 1+2+...+n = n*(n+1)/2
 * - Product: Can overflow, use XOR instead
 * 
 * Applications:
 * - Find missing number
 * - Find two missing numbers
 * - Single number (all others appear twice)
 * - Subset generation using bits
 */
public class MathematicalTechnique {
    
    public static void main(String[] args) {
        // Missing number
        int[] arr1 = {1, 2, 4, 5};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Missing number (1 to 5): " + findMissingNumber(arr1, 5));
        
        // Single number (XOR technique)
        int[] arr2 = {4, 1, 2, 1, 2};
        System.out.println("\n\nArray: " + Arrays.toString(arr2));
        System.out.println("Single number (others appear twice): " + singleNumber(arr2));
        
        // Two non-repeating numbers
        int[] arr3 = {2, 3, 7, 9, 11, 2, 3, 11};
        System.out.println("\n\nArray: " + Arrays.toString(arr3));
        System.out.println("Two unique numbers: " + Arrays.toString(twoUniqueNumbers(arr3)));
        
        // Missing and repeating
        int[] arr4 = {1, 2, 2, 4};
        System.out.println("\n\nArray: " + Arrays.toString(arr4));
        int[] result = findMissingAndRepeatingMath(arr4, 4);
        System.out.println("Missing: " + result[0] + ", Repeating: " + result[1]);
        
        // Count set bits
        int[] arr5 = {1, 2, 3, 4, 5};
        System.out.println("\n\nArray: " + Arrays.toString(arr5));
        System.out.println("Total set bits: " + countTotalSetBits(arr5));
        
        // Power set
        int[] arr6 = {1, 2, 3};
        System.out.println("\n\nArray: " + Arrays.toString(arr6));
        System.out.println("All subsets:");
        printAllSubsets(arr6);
        
        // GCD of array
        int[] arr7 = {12, 15, 18};
        System.out.println("\n\nArray: " + Arrays.toString(arr7));
        System.out.println("GCD: " + gcdOfArray(arr7));
    }
    
    /**
     * Find missing number using sum formula
     * Concept: expectedSum - actualSum = missing
     */
    public static int findMissingNumber(int[] arr, int n) {
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for (int num : arr) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    /**
     * Find missing number using XOR
     * Concept: a ^ a = 0, so XOR all numbers and array elements
     * Benefit: No overflow issues
     */
    public static int findMissingNumberXOR(int[] arr, int n) {
        int xor = 0;
        
        // XOR all numbers from 1 to n
        for (int i = 1; i <= n; i++) {
            xor ^= i;
        }
        
        // XOR with array elements
        for (int num : arr) {
            xor ^= num;
        }
        
        return xor;
    }
    
    /**
     * Single Number - Find element appearing once (others appear twice)
     * Concept: XOR all elements, duplicates cancel out
     */
    public static int singleNumber(int[] arr) {
        int result = 0;
        
        for (int num : arr) {
            result ^= num;
        }
        
        return result;
    }
    
    /**
     * Find two non-repeating numbers (others appear twice)
     * Concept: XOR all to get xor = a ^ b
     * Find rightmost set bit in xor to divide numbers into two groups
     */
    public static int[] twoUniqueNumbers(int[] arr) {
        int xor = 0;
        
        // XOR all elements
        for (int num : arr) {
            xor ^= num;
        }
        
        // Find rightmost set bit
        int rightmostBit = xor & -xor;
        
        // Divide into two groups and XOR separately
        int num1 = 0, num2 = 0;
        
        for (int num : arr) {
            if ((num & rightmostBit) != 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }
        
        return new int[]{num1, num2};
    }
    
    /**
     * Find missing and repeating using math
     * Concept: Let missing = x, repeating = y
     * sum - expectedSum = y - x ... (1)
     * sumSquares - expectedSumSquares = y² - x² ... (2)
     * Solve equations
     */
    public static int[] findMissingAndRepeatingMath(int[] arr, int n) {
        long sum = 0, sumSquares = 0;
        
        for (int num : arr) {
            sum += num;
            sumSquares += (long) num * num;
        }
        
        long expectedSum = (long) n * (n + 1) / 2;
        long expectedSumSquares = (long) n * (n + 1) * (2 * n + 1) / 6;
        
        long diff = sum - expectedSum; // y - x
        long diffSquares = sumSquares - expectedSumSquares; // y² - x²
        
        // y² - x² = (y - x)(y + x)
        long sumPair = diffSquares / diff; // y + x
        
        int repeating = (int) ((diff + sumPair) / 2);
        int missing = (int) (repeating - diff);
        
        return new int[]{missing, repeating};
    }
    
    /**
     * Count total set bits in all numbers
     */
    public static int countTotalSetBits(int[] arr) {
        int total = 0;
        
        for (int num : arr) {
            total += Integer.bitCount(num);
        }
        
        return total;
    }
    
    /**
     * Count set bits manually
     */
    public static int countSetBits(int n) {
        int count = 0;
        
        while (n > 0) {
            count += (n & 1);
            n >>= 1;
        }
        
        return count;
    }
    
    /**
     * Print all subsets using bit manipulation
     * Concept: For n elements, there are 2^n subsets
     * Each number from 0 to 2^n-1 represents a subset
     * Bit i set means include element i
     */
    public static void printAllSubsets(int[] arr) {
        int n = arr.length;
        int totalSubsets = 1 << n; // 2^n
        
        for (int i = 0; i < totalSubsets; i++) {
            System.out.print("{ ");
            
            for (int j = 0; j < n; j++) {
                // Check if jth bit is set
                if ((i & (1 << j)) != 0) {
                    System.out.print(arr[j] + " ");
                }
            }
            
            System.out.println("}");
        }
    }
    
    /**
     * GCD of two numbers
     */
    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    
    /**
     * GCD of array
     */
    public static int gcdOfArray(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int result = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            result = gcd(result, arr[i]);
            
            if (result == 1) break; // Optimization
        }
        
        return result;
    }
    
    /**
     * LCM of two numbers
     */
    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }
    
    /**
     * Check if number is power of 2
     * Concept: Power of 2 has only one set bit
     */
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    /**
     * Find position of rightmost set bit
     */
    public static int rightmostSetBit(int n) {
        if (n == 0) return -1;
        
        return (int) (Math.log(n & -n) / Math.log(2)) + 1;
    }
    
    /**
     * Toggle bit at position
     */
    public static int toggleBit(int n, int pos) {
        return n ^ (1 << pos);
    }
    
    /**
     * Set bit at position
     */
    public static int setBit(int n, int pos) {
        return n | (1 << pos);
    }
    
    /**
     * Clear bit at position
     */
    public static int clearBit(int n, int pos) {
        return n & ~(1 << pos);
    }
    
    /**
     * Check if bit is set at position
     */
    public static boolean isBitSet(int n, int pos) {
        return (n & (1 << pos)) != 0;
    }
    
    /**
     * Count pairs with XOR = k
     */
    public static int countPairsWithXOR(int[] arr, int k) {
        java.util.Set<Integer> set = new java.util.HashSet<>();
        int count = 0;
        
        for (int num : arr) {
            // If (num ^ k) exists, they form a pair with XOR = k
            if (set.contains(num ^ k)) {
                count++;
            }
            set.add(num);
        }
        
        return count;
    }
    
    /**
     * Maximum XOR of two elements
     */
    public static int maxXOR(int[] arr) {
        int max = 0;
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                max = Math.max(max, arr[i] ^ arr[j]);
            }
        }
        
        return max;
    }
}
