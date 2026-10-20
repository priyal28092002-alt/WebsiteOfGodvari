//Program to find the sum of array elements

package com.example.arrays;

import java.util.Scanner;

public class SumArrayElements {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the size of the array: ");
		int size = sc.nextInt();

		int[] array = new int[size];

		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}

		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum = sum + array[i];
		}

		System.out.println("The sum of the array is: " + sum);

		sc.close();
	}
}
