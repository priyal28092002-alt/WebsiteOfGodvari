package com.example.arrays;

import java.util.Arrays;
import java.util.Stack;

/**
 * CORE CONCEPT: Stack-Based Array Problems
 * 
 * Use when: Need to track previous elements, monotonic sequences
 * Time Complexity: O(n) - each element pushed/popped once
 * 
 * Pattern Recognition:
 * - Next/Previous Greater/Smaller Element
 * - Stock span problems
 * - Histogram problems
 * - Celebrity problems
 * - Expression evaluation
 * 
 * Monotonic Stack: Stack maintaining increasing/decreasing order
 * - Increasing stack: For finding next smaller
 * - Decreasing stack: For finding next greater
 * 
 * Applications:
 * - Next Greater Element (NGE)
 * - Stock Span
 * - Largest Rectangle in Histogram
 * - Rain Water Trapping
 */
public class StackBasedTechnique {
    
    public static void main(String[] args) {
        int[] arr = {4, 5, 2, 10, 8};
        System.out.println("Array: " + Arrays.toString(arr));
        
        // Next Greater Element
        int[] nge = nextGreaterElement(arr);
        System.out.println("Next Greater: " + Arrays.toString(nge));
        
        // Previous Smaller Element
        int[] pse = previousSmallerElement(arr);
        System.out.println("Previous Smaller: " + Arrays.toString(pse));
        
        // Stock Span
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        System.out.println("\n\nStock Prices: " + Arrays.toString(prices));
        int[] span = stockSpan(prices);
        System.out.println("Stock Span: " + Arrays.toString(span));
        
        // Largest Rectangle in Histogram
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println("\n\nHistogram: " + Arrays.toString(heights));
        System.out.println("Largest rectangle area: " + largestRectangle(heights));
        
        // Rain Water Trapping
        int[] elevation = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("\n\nElevation: " + Arrays.toString(elevation));
        System.out.println("Water trapped: " + trapRainWater(elevation));
        
        // Celebrity problem
        int[][] party = {
            {0, 1, 1, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 1, 0}
        };
        System.out.println("\n\nCelebrity Problem:");
        System.out.println("Celebrity: " + findCelebrity(party));
    }
    
    /**
     * Next Greater Element (NGE)
     * Concept: Maintain decreasing stack, pop when current is greater
     * 
     * Stack stores indices. When arr[i] > arr[stack.top()]:
     * - arr[i] is NGE for stack.top()
     * - Pop and continue
     */
    public static int[] nextGreaterElement(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        
        int n = arr.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // Pop elements smaller than current
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                result[stack.pop()] = arr[i];
            }
            
            stack.push(i);
        }
        
        return result;
    }
    
    /**
     * Previous Smaller Element
     * Concept: Maintain increasing stack from left
     */
    public static int[] previousSmallerElement(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        
        int n = arr.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // Pop elements >= current
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            
            if (!stack.isEmpty()) {
                result[i] = arr[stack.peek()];
            }
            
            stack.push(i);
        }
        
        return result;
    }
    
    /**
     * Stock Span Problem
     * Span[i] = Number of consecutive days before i where price <= price[i]
     * 
     * Concept: For each day, find previous day with higher price
     * Use stack to maintain decreasing sequence
     */
    public static int[] stockSpan(int[] prices) {
        if (prices == null || prices.length == 0) return new int[0];
        
        int n = prices.length;
        int[] span = new int[n];
        
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            // Pop days with price <= current
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }
            
            // Span = distance to previous higher price day
            span[i] = stack.isEmpty() ? (i + 1) : (i - stack.peek());
            
            stack.push(i);
        }
        
        return span;
    }
    
    /**
     * Largest Rectangle in Histogram
     * Concept: For each bar, find area with it as minimum height
     * Need: Left and right boundary where height >= current
     * 
     * Use stack to find previous/next smaller elements
     */
    public static int largestRectangle(int[] heights) {
        if (heights == null || heights.length == 0) return 0;
        
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        
        for (int i = 0; i < n; i++) {
            // Pop taller bars and calculate area
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, height * width);
            }
            
            stack.push(i);
        }
        
        // Process remaining bars
        while (!stack.isEmpty()) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? n : (n - stack.peek() - 1);
            maxArea = Math.max(maxArea, height * width);
        }
        
        return maxArea;
    }
    
    /**
     * Rain Water Trapping
     * Concept: Water at position i = min(maxLeft, maxRight) - height[i]
     * 
     * Two approaches:
     * 1. Precompute max arrays: O(n) space
     * 2. Two pointer: O(1) space
     */
    public static int trapRainWater(int[] height) {
        if (height == null || height.length < 3) return 0;
        
        int n = height.length;
        
        // Precompute max to left and right
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];
        
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }
        
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }
        
        // Calculate trapped water
        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }
        
        return water;
    }
    
    /**
     * Rain Water Trapping - Two Pointer (O(1) space)
     */
    public static int trapRainWaterOptimized(int[] height) {
        if (height == null || height.length < 3) return 0;
        
        int left = 0, right = height.length - 1;
        int maxLeft = 0, maxRight = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= maxLeft) {
                    maxLeft = height[left];
                } else {
                    water += maxLeft - height[left];
                }
                left++;
            } else {
                if (height[right] >= maxRight) {
                    maxRight = height[right];
                } else {
                    water += maxRight - height[right];
                }
                right--;
            }
        }
        
        return water;
    }
    
    /**
     * Celebrity Problem
     * Celebrity: Known by everyone, knows nobody
     * 
     * Matrix[i][j] = 1 means i knows j
     * 
     * Concept: Use stack to eliminate non-celebrities
     */
    public static int findCelebrity(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return -1;
        
        int n = matrix.length;
        Stack<Integer> stack = new Stack<>();
        
        // Push all people
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }
        
        // Eliminate non-celebrities
        while (stack.size() > 1) {
            int a = stack.pop();
            int b = stack.pop();
            
            if (matrix[a][b] == 1) {
                // a knows b, so a is not celebrity
                stack.push(b);
            } else {
                // a doesn't know b, so b is not celebrity
                stack.push(a);
            }
        }
        
        // Verify candidate
        int candidate = stack.pop();
        
        for (int i = 0; i < n; i++) {
            if (i != candidate) {
                // Candidate should not know anyone
                // Everyone should know candidate
                if (matrix[candidate][i] == 1 || matrix[i][candidate] == 0) {
                    return -1;
                }
            }
        }
        
        return candidate;
    }
    
    /**
     * Maximum of minimum for every window size
     * For each window size k, find max of all minimums
     */
    public static int[] maxOfMinWindows(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        
        int n = arr.length;
        int[] result = new int[n];
        
        // Find previous and next smaller for each element
        int[] left = new int[n];
        int[] right = new int[n];
        
        Stack<Integer> stack = new Stack<>();
        
        // Fill left - previous smaller
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        
        stack.clear();
        
        // Fill right - next smaller
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        
        // For each element, it's minimum for window size (right[i] - left[i] - 1)
        Arrays.fill(result, Integer.MIN_VALUE);
        
        for (int i = 0; i < n; i++) {
            int windowSize = right[i] - left[i] - 1;
            result[windowSize - 1] = Math.max(result[windowSize - 1], arr[i]);
        }
        
        // Fill gaps - larger window can't have smaller maximum
        for (int i = n - 2; i >= 0; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }
        
        return result;
    }
}
