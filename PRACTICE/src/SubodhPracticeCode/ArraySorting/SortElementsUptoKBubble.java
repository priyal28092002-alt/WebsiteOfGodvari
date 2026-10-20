//Program to sort the elements of the array in ascending order till kth index 
//and after that in descending order.

package com.example.ArraySorting;

import java.util.Scanner;

public class SortElementsUptoKBubble {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size = sc.nextInt();
		int[] array = new int[size];

		System.out.println("Enter the array elements:");
		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}
		System.out.print("Enter the index:");
		int k = sc.nextInt();

		for (int i = 0; i < k; i++) {
			for (int j = 0; j < k-1; j++) {
				if (array[j] > array[j + 1]) {
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		for (int i = k; i < array.length; i++) {
			for (int j = k; j < array.length - 1; j++) {
				if (array[j] < array[j + 1]) {
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}

		System.out.println("Array after sorting the elemnts:");
		printarray(array);
	}

	public static void printarray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
