//Program to find the largest number from each row of the matrix

package com.example.TwoDimensionalArray;

import java.util.Scanner;

public class LargestNumRowMatrix {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the no. of rows for the matrix:");
		int o_size = sc.nextInt();
		System.out.print("Enter the no. of columns for the matrix:");
		int i_size = sc.nextInt();

		int[][] matrix = new int[o_size][i_size]; // 2D matrix

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.println("Enter the elements for index " + i + " " + j);
				matrix[i][j] = sc.nextInt();
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			int largest = Integer.MIN_VALUE;
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] > largest) {
					largest = matrix[i][j];
				}
			}
			System.out.println("The largest element of the " + (i + 1) + " row is: " + largest);
		}

		sc.close();
	}
}
