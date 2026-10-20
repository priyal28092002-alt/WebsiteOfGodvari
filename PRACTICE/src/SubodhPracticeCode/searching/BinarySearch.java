package com.example.searching;

import java.util.Scanner;

public class BinarySearch {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of the array: ");
		int size  = sc.nextInt();
		
		int[] array = new int[size];
		System.out.println("Enter the elements of the array: ");
		for(int i=0; i<array.length; i++) {
			array[i] = sc.nextInt();
		}
		
		System.out.println("Original array before sorting: ");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		
		System.out.println();
		System.out.println("Enter the element to be searched: ");
		int element = sc.nextInt();
		
		int index = binarySearch(array, element);
		
		if (index != -1) {
			System.out.println("Element found at index: " + index);
		} else {
			System.out.println("Element not found in the array.");
		}
		
		sc.close();
	}
	
	public static int binarySearch(int[] array, int element) {
		int low = 0;
		int high = array.length - 1;
		int mid = 0;

		while (low <= high) {
			mid = (low + high) / 2;

			if (array[mid] == element) {
				return mid;
			} else if (array[mid] < element) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1;
	}
}
