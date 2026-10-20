//Program to reverse each element in the 2D Array

package com.example.TwoDimensionalArray;

import java.util.Scanner;

public class ReverseArrayElements {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the size of 2D array:");
		int o_size = sc.nextInt();
		System.out.print("Enter the size of 1D array:");
		int i_size = sc.nextInt();

		int[][] array = new int[o_size][i_size]; // 2D Array

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.println("Enter the elements for index " + i + " " + j);
				array[i][j] = sc.nextInt();
			}
		}

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				int reverse = 0;

				int n = array[i][j];
				while (n != 0) {
					int digit = n % 10;
					reverse = reverse * 10 + digit;
					n /= 10;
				}
				array[i][j] = reverse;
			}
		}

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}

		sc.close();
	}
}
