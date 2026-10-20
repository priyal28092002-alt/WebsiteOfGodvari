//Program to find the position at which the matrix element is present and then finds its sum
//If the sum is even then find the sum of the digits of the even elements
//If the sum is odd then find the sum of the digits of the even elements

package com.example.TwoDimensionalArray;

import java.util.Scanner;

public class SumOfEvenOrOddElementsMatrix {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the no. of rows for the Matrix : ");
		int row = sc.nextInt();
		System.out.print("Enter the no. of columns for the Matrix :");
		int col = sc.nextInt();

		System.out.print("Enter the elemnt: ");
		int element = sc.nextInt();

		int[][] matrix = new int[row][col];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.println("Enter the elements for index " + i + " " + j);
				matrix[i][j] = sc.nextInt();
			}
		}
        
		//TODO: Find the position at which the matrix element is present and then finds its sum
		int sumRC = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (element == matrix[i][j]) {
					sumRC += (i + j);
				}
			}
		}
        
		//TODO: If the sum is even then find the sum of the digits of the even elements
		//If the sum is odd then find the sum of the digits of the even elements
		int sumEl = 0;
		if (sumRC % 2 == 0) {
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (matrix[i][j] % 2 == 0) {
						sumEl += sumOfDigits(matrix[i][j]);
					}
				}
			}
		} else {
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (matrix[i][j] % 2 != 0) {
						sumEl += sumOfDigits(matrix[i][j]);
					}
				}
			}

		}
		System.out.println(sumEl);
		
		sc.close();
	}

	public static int sumOfDigits(int n) {
		int sum = 0;
		while (n != 0) {
			int digit = n % 10;
			sum += digit;
			n /= 10;
		}
		return sum;
	}
}
