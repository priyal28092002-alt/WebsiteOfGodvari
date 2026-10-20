package com.example.arrays;

import java.util.Scanner;

//Question: Given an array of integers and an integer K, find the number of subarrays whose sum is equal to K. 
//Example: Array = [1, 2, 3] K=3 Subarrays with sum = 3 > [1,2], [3] Output > 2

public class SubarraySum {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// Input size
		System.out.print("Enter number of elements: ");
		int n = sc.nextInt();

		int[] arr = new int[n];

		// Input array elements
		System.out.println("Enter array elements:");
		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}

		// Input K
		System.out.print("Enter value of K: ");
		int k = sc.nextInt();

		int count = 0;

		// Generate all subarrays
		for (int i = 0; i < n; i++) {
			int sum = 0;

			for (int j = i; j < n; j++) {
				sum += arr[j];

				if (sum == k) {
					count++;
				}
			}
		}

		System.out.println("Number of subarrays with sum = " + k + " is: " + count);
	}
}
