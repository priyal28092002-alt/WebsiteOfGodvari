//Sorting -> Insertion Sort (includes shifting rather than sorting) in ascending order

package com.example.ArraySorting;

import java.util.Scanner;

public class InsertionSortASC {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size = sc.nextInt();
		int[] array = new int[size];

		System.out.println("Enter the array elements:");
		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}
		sc.close();
        
		//i initializes from 1 since it will be compared with the previous element
		for (int i = 1; i < array.length; i++) {
			int temp = array[i];
			int j = i;
			while (j > 0 && array[j - 1] > temp) {
				array[j] = array[j - 1];
				j--;
			}
			array[j] = temp;
		}

		System.out.println("Array after sorting the elemnts (Insertion Sort):");
		printarray(array);
	}

	public static void printarray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
