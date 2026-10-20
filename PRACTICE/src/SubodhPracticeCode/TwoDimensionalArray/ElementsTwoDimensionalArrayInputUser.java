//Program to print all the elements of 2D array by taking the input from user.

package com.example.TwoDimensionalArray;

import java.util.Scanner;

public class ElementsTwoDimensionalArrayInputUser {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the size of 2D array:");
		int o_size=sc.nextInt();
		System.out.print("Enter the size of 1D array:");
		int i_size=sc.nextInt();
		
		int[][] array=new int[o_size][i_size]; //2D Array
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.println("Enter the elements for index "+i+" "+j);
				array[i][j]=sc.nextInt();
			}
		}
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.println(array[i][j]);
			}
		}
		
		sc.close();
	}
}
