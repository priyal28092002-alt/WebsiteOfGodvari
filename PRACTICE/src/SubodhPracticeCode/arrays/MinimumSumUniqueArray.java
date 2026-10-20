/*You are given an array A of size N. Y
You can perform the following operations(possibly zero) on A 
until all of its elements are unique. 
Select an index i such that 0<=i<N. 
Increase the value of A[i] by some value k such that there exists 
no integer in A whose value is equal to A[i] +k. 
Find the minimum sum of elements of A after applying the above operations. 
Note: You can choose the value of k before each operation. 

Write a program in Java for the above question.

Case 1: Input- 4,4,4,4,4 Output- 30 */

package com.example.arrays;

import java.util.Arrays;

public class MinimumSumUniqueArray {
    public static int minSumOfUniqueArray(int[] A) {
        Arrays.sort(A);
        
        int n = A.length;
        int minSum = A[0];
        
        for (int i = 1; i < n; i++) {
            if (A[i] <= A[i - 1]) {
                A[i] = A[i - 1] + 1;
            }
            minSum += A[i];
        }
        
        return minSum;
    }

    public static void main(String[] args) {
        int[] A = {4, 4, 4, 4, 4};
        int result = minSumOfUniqueArray(A);
        System.out.println("Minimum sum of unique elements: " + result);
    }
}