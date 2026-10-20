package com.example.arrays;

import java.util.Arrays;

/**
 * CORE CONCEPT: Sorting-Based Problem Solving
 * 
 * Use when: Need ordering, finding pairs/triplets, interval merging
 * Time Complexity: O(n log n) for sorting + O(n) for processing
 * 
 * Pattern Recognition:
 * - Sorting helps reveal patterns
 * - Pair/triplet sum problems
 * - Merging intervals
 * - Meeting rooms
 * - Activity selection
 * - Finding closest elements
 * 
 * Key Insight: Many O(n²) problems reduce to O(n log n) after sorting
 * 
 * Applications:
 * - Three sum problem
 * - Merge intervals
 * - Maximum guests at party
 * - Minimum platforms required
 */
public class SortingBasedTechnique {
    
    public static void main(String[] args) {
        // Three Sum
        int[] arr = {-1, 0, 1, 2, -1, -4};
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Three sum combinations (sum = 0):");
        threeSum(arr, 0);
        
        // Merge Intervals
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println("\n\nIntervals:");
        printIntervals(intervals);
        int[][] merged = mergeIntervals(intervals);
        System.out.println("After merging:");
        printIntervals(merged);
        
        // Meeting Rooms
        int[][] meetings = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("\n\nMeetings:");
        printIntervals(meetings);
        System.out.println("Can attend all: " + canAttendAllMeetings(meetings));
        
        // Minimum Platforms
        int[] arrivals = {900, 940, 950, 1100, 1500, 1800};
        int[] departures = {910, 1200, 1120, 1130, 1900, 2000};
        System.out.println("\n\nArrivals:   " + Arrays.toString(arrivals));
        System.out.println("Departures: " + Arrays.toString(departures));
        System.out.println("Minimum platforms: " + minimumPlatforms(arrivals, departures));
        
        // Closest pair
        int[] arr2 = {1, 3, 6, 7, 10, 15};
        int target = 10;
        System.out.println("\n\nArray: " + Arrays.toString(arr2));
        System.out.println("Closest pair with sum = " + target + ": " + 
                          Arrays.toString(closestPair(arr2, target)));
    }
    
    /**
     * Three Sum Problem: Find all triplets with sum = target
     * Concept: Sort + fix one element + two pointer on remaining
     * Time: O(n²), Space: O(1)
     */
    public static void threeSum(int[] arr, int target) {
        if (arr == null || arr.length < 3) return;
        
        Arrays.sort(arr);
        
        for (int i = 0; i < arr.length - 2; i++) {
            // Skip duplicates for first element
            if (i > 0 && arr[i] == arr[i - 1]) continue;
            
            // Two pointer approach on remaining array
            int left = i + 1;
            int right = arr.length - 1;
            
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                
                if (sum == target) {
                    System.out.println("[" + arr[i] + ", " + arr[left] + ", " + arr[right] + "]");
                    
                    // Skip duplicates
                    while (left < right && arr[left] == arr[left + 1]) left++;
                    while (left < right && arr[right] == arr[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
    }
    
    /**
     * Merge Overlapping Intervals
     * Concept: Sort by start time, merge if current overlaps with previous
     * Time: O(n log n)
     */
    public static int[][] mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) return intervals;
        
        // Sort by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        java.util.List<int[]> merged = new java.util.ArrayList<>();
        int[] current = intervals[0];
        merged.add(current);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];
            
            if (next[0] <= current[1]) {
                // Overlapping - merge
                current[1] = Math.max(current[1], next[1]);
            } else {
                // Non-overlapping - add new interval
                current = next;
                merged.add(current);
            }
        }
        
        return merged.toArray(new int[merged.size()][]);
    }
    
    /**
     * Can Attend All Meetings
     * Concept: Sort intervals, check if any overlap
     */
    public static boolean canAttendAllMeetings(int[][] meetings) {
        if (meetings == null || meetings.length <= 1) return true;
        
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);
        
        for (int i = 1; i < meetings.length; i++) {
            if (meetings[i][0] < meetings[i - 1][1]) {
                return false; // Overlap found
            }
        }
        
        return true;
    }
    
    /**
     * Minimum Meeting Rooms Required
     * Concept: Count overlapping intervals at any time
     */
    public static int minMeetingRooms(int[][] meetings) {
        if (meetings == null || meetings.length == 0) return 0;
        
        int n = meetings.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        
        for (int i = 0; i < n; i++) {
            starts[i] = meetings[i][0];
            ends[i] = meetings[i][1];
        }
        
        Arrays.sort(starts);
        Arrays.sort(ends);
        
        int rooms = 0;
        int maxRooms = 0;
        int i = 0, j = 0;
        
        while (i < n) {
            if (starts[i] < ends[j]) {
                rooms++;
                maxRooms = Math.max(maxRooms, rooms);
                i++;
            } else {
                rooms--;
                j++;
            }
        }
        
        return maxRooms;
    }
    
    /**
     * Minimum Platforms Required (Railway Station)
     * Concept: Similar to meeting rooms - count maximum overlap
     */
    public static int minimumPlatforms(int[] arrivals, int[] departures) {
        if (arrivals == null || departures == null) return 0;
        
        Arrays.sort(arrivals);
        Arrays.sort(departures);
        
        int platforms = 0;
        int maxPlatforms = 0;
        int i = 0, j = 0;
        int n = arrivals.length;
        
        while (i < n && j < n) {
            if (arrivals[i] <= departures[j]) {
                platforms++;
                maxPlatforms = Math.max(maxPlatforms, platforms);
                i++;
            } else {
                platforms--;
                j++;
            }
        }
        
        return maxPlatforms;
    }
    
    /**
     * Find pair with sum closest to target
     * Concept: Sort + two pointer, track minimum difference
     */
    public static int[] closestPair(int[] arr, int target) {
        if (arr == null || arr.length < 2) return new int[0];
        
        Arrays.sort(arr);
        
        int left = 0;
        int right = arr.length - 1;
        int minDiff = Integer.MAX_VALUE;
        int[] result = new int[2];
        
        while (left < right) {
            int sum = arr[left] + arr[right];
            int diff = Math.abs(sum - target);
            
            if (diff < minDiff) {
                minDiff = diff;
                result[0] = arr[left];
                result[1] = arr[right];
            }
            
            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                break; // Exact match
            }
        }
        
        return result;
    }
    
    /**
     * Minimum difference between any pair
     * Concept: Sort, minimum difference is between adjacent elements
     */
    public static int minDifference(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        
        Arrays.sort(arr);
        
        int minDiff = Integer.MAX_VALUE;
        
        for (int i = 1; i < arr.length; i++) {
            minDiff = Math.min(minDiff, arr[i] - arr[i - 1]);
        }
        
        return minDiff;
    }
    
    /**
     * Maximum gap between elements after sorting
     * Concept: Bucket sort approach for O(n) or simple sort O(n log n)
     */
    public static int maximumGap(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        
        Arrays.sort(arr);
        
        int maxGap = 0;
        
        for (int i = 1; i < arr.length; i++) {
            maxGap = Math.max(maxGap, arr[i] - arr[i - 1]);
        }
        
        return maxGap;
    }
    
    private static void printIntervals(int[][] intervals) {
        for (int[] interval : intervals) {
            System.out.println("[" + interval[0] + ", " + interval[1] + "]");
        }
    }
}
